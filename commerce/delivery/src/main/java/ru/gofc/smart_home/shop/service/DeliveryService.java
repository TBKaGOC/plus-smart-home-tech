package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.client.OrderClient;
import ru.gofc.smart_home.shop.client.WarehouseClient;
import ru.gofc.smart_home.shop.dto.DeliveryDto;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.dto.enums.DeliveryState;
import ru.gofc.smart_home.shop.exception.NoDeliveryFoundException;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.mapper.DeliveryMapper;
import ru.gofc.smart_home.shop.model.Delivery;
import ru.gofc.smart_home.shop.repository.DeliveryRepository;
import ru.gofc.smart_home.shop.request.ShippedToDeliveryRequest;

import java.util.UUID;

@Setter
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@ConfigurationProperties("costConst")
public class DeliveryService {
    final DeliveryRepository repository;
    final OrderClient order;
    final WarehouseClient warehouse;
    final Double baseCost;
    final Double weightCoff;
    final Double volumeCoff;

    @Transactional
    public DeliveryDto planDelivery(DeliveryDto delivery) {
        log.info("Создание новой доставки");
        delivery.setDeliveryId(UUID.randomUUID().toString());
        delivery.setState(DeliveryState.CREATED);
        return DeliveryMapper.mapToDto(repository.save(DeliveryMapper.mapToEntity(delivery)));
    }

    @Transactional
    public void deliverySuccessful(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        log.info("Изменение статуса на DELIVERED доставки {}", id);
        order.delivery(setState(id, DeliveryState.DELIVERED).getOrderId());
    }

    @Transactional
    public void deliveryPicked(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        log.info("Изменение статуса на IN_PROGRESS доставки {}", id);
        String orderId = setState(id, DeliveryState.IN_PROGRESS).getOrderId();
        order.assembly(orderId);
        warehouse.shippedToDelivery(new ShippedToDeliveryRequest(orderId, id));
    }

    @Transactional
    public void deliveryFailed(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        log.info("Изменение статуса на FAILED доставки {}", id);
        order.deliveryFailed(setState(id, DeliveryState.FAILED).getOrderId());
    }

    public Double deliveryCost(OrderDto order) throws NoDeliveryFoundException {
        String id = order.getOrderId();
        log.info("Рассчёт стоимости доставки заказа {}", id);

        Delivery delivery = repository.findById(order.getDeliveryId()).orElseThrow(
                () -> new NoDeliveryFoundException("Не найдена доставка" + order.getDeliveryId())
        );
        double resultCost = baseCost;
        log.debug("Базовая цена доставки {} для заказа {}", resultCost, id);

        int coff = 1;
        if (delivery.getFrom().getCountry().equals("ADDRESS_2")) {
            coff = 2;
        }
        resultCost *= coff;
        log.debug("Заказ: {}, коэффициент: {}, из-за: адрес склада({}), итого: {}",
                id, coff, delivery.getFrom(), resultCost);

        double toCoff = 1.0;
        if (!(delivery.getFrom().getStreet().equals(delivery.getTo().getStreet()) &&
        delivery.getFrom().getCity().equals(delivery.getTo().getCity()))) {
            toCoff = 1.2;
        }

        double fragileKoff = 1.0;
        if (order.getFragile()) {
            fragileKoff = 1.2;
        }
        resultCost *= fragileKoff;
        log.debug("Заказ: {}, коэффициент: {}, из-за: хрупкость({}), итого: {}",
                id, fragileKoff, order.getFragile(), resultCost);

        resultCost += order.getDeliveryWeight() * weightCoff;
        log.debug("Заказ: {}, на: {}, из-за: вес({} по {}), итого: {}",
                id, order.getDeliveryWeight() * weightCoff, order.getDeliveryWeight(), weightCoff, resultCost);
        resultCost += order.getDeliveryVolume() * volumeCoff;
        log.debug("Заказ: {}, на: {}, из-за: объём({} по {}), итого: {}",
                id, order.getDeliveryVolume() * volumeCoff, order.getDeliveryVolume(), volumeCoff, resultCost);

        log.debug("Заказ: {}, коэффициент: {}, из-за: адрес клиента({}), итого: {}",
                id, toCoff, delivery.getTo(), resultCost);
        return resultCost * toCoff;
    }

    private Delivery setState(String id, DeliveryState state) throws NoDeliveryFoundException {
        Delivery delivery = repository.findById(id).orElseThrow(
                () -> new NoDeliveryFoundException("Не найдена доставка " + id)
        );
        delivery.setState(state);

        repository.deleteById(id);
        repository.save(delivery);
        return delivery;
    }
}
