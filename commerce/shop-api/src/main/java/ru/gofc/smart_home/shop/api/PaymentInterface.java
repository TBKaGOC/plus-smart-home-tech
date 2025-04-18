package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.dto.PaymentDto;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.exception.NotEnoughInfoInOrderToCalculateException;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;

@RequestMapping("/api/v1/payment")
public interface PaymentInterface {
    @PostMapping
    PaymentDto payment(@RequestBody OrderDto order) throws NotEnoughInfoInOrderToCalculateException, NoOrderFoundException, ProductNotFoundException;

    @PostMapping("/totalCost")
    Double getTotalCost(@RequestBody OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException;

    @PostMapping("/refund")
    void paymentSuccess(@RequestBody String id) throws NoOrderFoundException;

    @PostMapping("/productCost")
    Double productCost(@RequestBody OrderDto order) throws NotEnoughInfoInOrderToCalculateException, ProductNotFoundException;

    @PostMapping("/failed")
    void paymentFailed(@RequestBody String id) throws NoOrderFoundException;
}
