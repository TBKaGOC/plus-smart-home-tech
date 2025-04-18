package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.gofc.smart_home.shop.api.DeliveryInterface;
import ru.gofc.smart_home.shop.dto.DeliveryDto;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.exception.NoDeliveryFoundException;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.service.DeliveryService;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryController implements DeliveryInterface {
    final DeliveryService service;

    @Override
    public DeliveryDto planDelivery(DeliveryDto delivery) {
        return service.planDelivery(delivery);
    }

    @Override
    public void deliverySuccessful(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        service.deliverySuccessful(id);
    }

    @Override
    public void deliveryPicked(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        service.deliveryPicked(id);
    }

    @Override
    public void deliveryFailed(String id) throws NoDeliveryFoundException, NoOrderFoundException {
        service.deliveryFailed(id);
    }

    @Override
    public Double deliveryCost(OrderDto order) throws NoDeliveryFoundException {
        return service.deliveryCost(order);
    }
}
