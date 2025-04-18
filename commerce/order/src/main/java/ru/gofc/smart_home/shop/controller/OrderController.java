package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.gofc.smart_home.shop.api.OrderInterface;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.CreateNewOrderRequest;
import ru.gofc.smart_home.shop.request.ProductReturnRequest;
import ru.gofc.smart_home.shop.service.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderController implements OrderInterface {
    final OrderService service;

    @Override
    public List<OrderDto> getClientOrders(String username) throws NotAuthorizedUserException {
        return service.getClientOrders(username);
    }

    @Override
    public OrderDto createNewOrder(CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException, ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException, NoDeliveryFoundException {
        return service.createNewOrder(request);
    }

    @Override
    public OrderDto productReturn(ProductReturnRequest request) throws NoOrderFoundException, ProductInShoppingCartLowQuantityInWarehouse {
        return service.productReturn(request);
    }

    @Override
    public OrderDto payment(String orderId) throws NoOrderFoundException {
        return service.payment(orderId);
    }

    @Override
    public OrderDto paymentFailed(String orderId) throws NoOrderFoundException {
        return service.paymentFailed(orderId);
    }

    @Override
    public OrderDto delivery(String orderId) throws NoOrderFoundException {
        return service.delivery(orderId);
    }

    @Override
    public OrderDto deliveryFailed(String orderId) throws NoOrderFoundException {
        return service.deliveryFailed(orderId);
    }

    @Override
    public OrderDto complete(String orderId) throws NoOrderFoundException {
        return service.complete(orderId);
    }

    @Override
    public OrderDto calculateTotalCost(String orderId) throws NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException {
        return service.calculateTotalCost(orderId);
    }

    @Override
    public OrderDto calculateDeliveryCost(String orderId) throws NoOrderFoundException, NoDeliveryFoundException {
        return service.calculateDeliveryCost(orderId);
    }

    @Override
    public OrderDto assembly(String orderId) throws NoOrderFoundException {
        return service.assembly(orderId);
    }

    @Override
    public OrderDto assemblyFailed(String orderId) throws NoOrderFoundException {
        return service.assemblyFailed(orderId);
    }
}
