package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.gofc.smart_home.shop.api.PaymentInterface;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.dto.PaymentDto;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.exception.NotEnoughInfoInOrderToCalculateException;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.service.PaymentService;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentController implements PaymentInterface {
    final PaymentService service;

    @Override
    public PaymentDto payment(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, NoOrderFoundException, ProductNotFoundException {
        return service.payment(order);
    }

    @Override
    public Double getTotalCost(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException {
        return service.getTotalCost(order);
    }

    @Override
    public void paymentSuccess(String id) throws NoOrderFoundException {
        service.paymentSuccess(id);
    }

    @Override
    public Double productCost(OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException {
        return service.productCost(order);
    }

    @Override
    public void paymentFailed(String id) throws NoOrderFoundException {
        service.paymentFailed(id);
    }
}
