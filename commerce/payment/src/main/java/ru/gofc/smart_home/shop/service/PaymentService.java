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
import ru.gofc.smart_home.shop.client.StoreClient;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.dto.PaymentDto;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.exception.NotEnoughInfoInOrderToCalculateException;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.mapper.PaymentMapper;
import ru.gofc.smart_home.shop.model.Payment;
import ru.gofc.smart_home.shop.model.PaymentStatus;
import ru.gofc.smart_home.shop.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;

@Setter
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties
@Service
public class PaymentService {
    final PaymentRepository repository;
    final OrderClient order;
    final StoreClient store;
    final Integer feePercent;

    @Transactional
    public PaymentDto payment(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, NoOrderFoundException, ProductNotFoundException {
        try {
            Payment payment = new Payment(
                    UUID.randomUUID().toString(),
                    order.getPaymentId(),
                    order.getProductPrice(),
                    order.getDeliveryPrice(),
                    order.getTotalPrice(),
                    PaymentStatus.PENDING
            );

            repository.save(payment);
            //Передача оплаты в платёжный шлюз
            return PaymentMapper.mapToDto(payment);
        } catch (Exception e) {
            paymentFailed(order.getOrderId());
            throw e;
        }
    }

    public Double getTotalCost(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException {
        log.info("Рассчёт общей стоимости заказа " + order.getOrderId());
        if (order.getDeliveryPrice() == null || order.getDeliveryPrice() <= 0 ||
        order.getProductPrice() == null || order.getProductPrice() <= 0) {
            throw new NotEnoughInfoInOrderToCalculateException(
                    "Недостаточно информации для определения общей стоимости заказа " + order.getOrderId()
            );
        }

        return order.getDeliveryPrice() + order.getProductPrice() + order.getProductPrice() * feePercent / 100.0;
    }

    @Transactional
    public void paymentSuccess(String id) throws NoOrderFoundException {
        log.info("Присвоение статуса SUCCESS оплате" + id);
        order.payment(setStatus(id, PaymentStatus.SUCCESS).getOrderId());
    }

    public Double productCost(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException {
        log.info("Рассчёт стоимости продуктов для заказа " + order.getOrderId());
        if (order.getProducts() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(
                    "Недостаточно информации для определения общей стоимости заказа " + order.getOrderId()
            );
        }

        double cost = 0.0;
        Map<String, Integer> products = order.getProducts();

        for (String id: products.keySet()) {
            cost += store.getProduct(id).getPrice() * products.get(id);
        }

        return cost;
    }

    @Transactional
    public void paymentFailed(String id) throws NoOrderFoundException {
        log.info("Присвоение статуса FAILED оплате " + id);
        order.paymentFailed(setStatus(id, PaymentStatus.FAILED).getOrderId());
    }

    private Payment setStatus(String id, PaymentStatus status) throws NoOrderFoundException {
        Payment payment = repository.findById(id).orElseThrow(
                () -> new NoOrderFoundException("Не найдена оплата " + id)
        );
        payment.setStatus(status);

        repository.deleteById(id);
        repository.save(payment);
        return payment;
    }
}
