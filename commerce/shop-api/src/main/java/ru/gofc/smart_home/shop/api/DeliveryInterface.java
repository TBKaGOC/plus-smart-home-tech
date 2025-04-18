package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gofc.smart_home.shop.dto.DeliveryDto;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.exception.NoDeliveryFoundException;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;

@RequestMapping("/api/v1/delivery")
public interface DeliveryInterface {
    @PutMapping
    DeliveryDto planDelivery(@RequestBody DeliveryDto delivery);

    @PostMapping("/successful")
    void deliverySuccessful(@RequestBody String id) throws NoDeliveryFoundException, NoOrderFoundException;

    @PostMapping("/picked")
    void deliveryPicked(@RequestBody String id) throws NoDeliveryFoundException, NoOrderFoundException;

    @PostMapping("/failed")
    void deliveryFailed(@RequestBody String id) throws NoDeliveryFoundException, NoOrderFoundException;

    @PostMapping("/cost")
    Double deliveryCost(@RequestBody OrderDto order) throws NoDeliveryFoundException;
}
