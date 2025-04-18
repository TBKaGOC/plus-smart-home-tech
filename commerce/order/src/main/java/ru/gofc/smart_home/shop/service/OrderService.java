package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.client.DeliveryClient;
import ru.gofc.smart_home.shop.client.PaymentClient;
import ru.gofc.smart_home.shop.client.WarehouseClient;
import ru.gofc.smart_home.shop.dto.BookedProductsDto;
import ru.gofc.smart_home.shop.dto.DeliveryDto;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.dto.enums.OrderState;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.mapper.OrderMapper;
import ru.gofc.smart_home.shop.model.Order;
import ru.gofc.smart_home.shop.model.OrderProductQuantity;
import ru.gofc.smart_home.shop.repository.OrderRepository;
import ru.gofc.smart_home.shop.request.AssemblyProductsForOrderRequest;
import ru.gofc.smart_home.shop.request.CreateNewOrderRequest;
import ru.gofc.smart_home.shop.request.ProductReturnRequest;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class OrderService {
    final OrderRepository repository;
    final WarehouseClient warehouse;
    final PaymentClient payment;
    final DeliveryClient delivery;

    public List<OrderDto> getClientOrders(String username) throws NotAuthorizedUserException {
        log.info("Получение заказов клиента " + username);
        if (username.isBlank()) {
            throw new NotAuthorizedUserException("Недопустимое имя пользователя");
        }

        return repository.findByUsername(username).stream()
                .map(OrderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto createNewOrder(CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException, ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException, NoDeliveryFoundException {
        log.info("Создание новго заказа по корзине " + request.getShoppingCart().getShoppingCartId());
        log.debug("Создание нового заказа по запросу: " + request);

        Order order = Order.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .products(OrderMapper.mapToQuantity(request.getShoppingCart().getProducts()))
                .state(OrderState.NEW)
                .build();
        DeliveryDto newDelivery = delivery.planDelivery(new DeliveryDto(
                null,
                null,
                warehouse.getAddress(),
                request.getDeliveryAddress(),
                order.getId()
        ));

        setDeliveryFields(order);

        order.setDeliveryPrice(delivery.deliveryCost(OrderMapper.mapToDto(order)));
        order.setProductPrice(payment.productCost(OrderMapper.mapToDto(order)));
        order.setTotalPrice(payment.getTotalCost(OrderMapper.mapToDto(order)));
        order.setDeliveryId(newDelivery.getDeliveryId());
        repository.save(order);

        log.debug("Сохранён заказ " + order);

        payment.payment(OrderMapper.mapToDto(order));
        return OrderMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto productReturn(ProductReturnRequest request) throws NoOrderFoundException, ProductInShoppingCartLowQuantityInWarehouse {
        log.info("Возвращение продуктов для заказа " + request.getOrderId());
        log.debug("Возвращение продуктов " + request.getProducts() + " для заказа " + request.getOrderId());

        Order order = repository.findById(request.getOrderId()).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ " + request.getOrderId())
        );

        order.setProducts(recalculateProducts(order, request.getProducts()));
        setDeliveryFields(order);
        order.setState(OrderState.PRODUCT_RETURNED);

        repository.deleteById(order.getId());
        repository.save(order);
        return OrderMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto payment(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на PAID заказа " + orderId);
        return setState(orderId, OrderState.PAID);
    }

    @Transactional
    public OrderDto paymentFailed(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на PAYMENT_FAILED заказа " + orderId);
        return setState(orderId, OrderState.PAYMENT_FAILED);
    }

    @Transactional
    public OrderDto delivery(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на DELIVERED заказа " + orderId);
        return setState(orderId, OrderState.DELIVERED);
    }

    @Transactional
    public OrderDto deliveryFailed(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на DELIVERY_FAILED заказа " + orderId);
        return setState(orderId, OrderState.DELIVERY_FAILED);
    }

    @Transactional
    public OrderDto complete(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на COMPLETED заказа " + orderId);
        return setState(orderId, OrderState.COMPLETED);
    }

    @Transactional
    public OrderDto calculateTotalCost(String orderId) throws NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException {
        log.info("Определение общей стоимости заказа " + orderId);
        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ " + orderId)
        );
        order.setTotalPrice(payment.getTotalCost(OrderMapper.mapToDto(order)));
        return OrderMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto calculateDeliveryCost(String orderId) throws NoOrderFoundException, NoDeliveryFoundException {
        log.info("Определение стоимости доставки заказа " + orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ " + orderId)
        );
        order.setDeliveryPrice(delivery.deliveryCost(OrderMapper.mapToDto(order)));

        repository.deleteById(orderId);
        repository.save(order);
        return OrderMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto assembly(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на ASSEMBLED заказа " + orderId);
        return setState(orderId, OrderState.ASSEMBLED);
    }

    @Transactional
    public OrderDto assemblyFailed(String orderId) throws NoOrderFoundException {
        log.info("Изменеие статуса на ASSEMBLY_FAILED заказа " + orderId);
        return setState(orderId, OrderState.ASSEMBLY_FAILED);
    }

    private OrderDto setState(String orderId, OrderState state) throws NoOrderFoundException {
        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ " + orderId)
        );
        order.setState(state);

        repository.deleteById(orderId);
        repository.save(order);
        return OrderMapper.mapToDto(order);
    }

    private void setDeliveryFields(Order order) throws ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException {
        BookedProductsDto booking = warehouse.assemblyProductsForOrder(new AssemblyProductsForOrderRequest(
                order.getId(), OrderMapper.mapToMap(order.getProducts())
        ));

        order.setDeliveryVolume(booking.getDeliveryVolume());
        order.setDeliveryWeight(booking.getDeliveryWeight());
        order.setFragile(booking.getFragile());
    }

    private List<OrderProductQuantity> recalculateProducts(Order order, Map<String, Integer> quantities) {
        List<OrderProductQuantity> result = new ArrayList<>();
        for (OrderProductQuantity product: order.getProducts()) {
            String id = product.getProductId();
            if (quantities.containsKey(id)) {
                if (!Objects.equals(product.getQuantity(), quantities.get(id))) {
                    product.setQuantity(product.getQuantity() - quantities.get(id));
                    result.add(product);
                }
            } else {
                result.add(product);
            }
        }
        return result;
    }
}
