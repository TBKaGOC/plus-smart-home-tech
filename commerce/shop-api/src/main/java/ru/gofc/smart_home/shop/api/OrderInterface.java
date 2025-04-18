package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.CreateNewOrderRequest;
import ru.gofc.smart_home.shop.request.ProductReturnRequest;

import java.util.List;

@RequestMapping("/api/v1/order")
public interface OrderInterface {
    @GetMapping
    List<OrderDto> getClientOrders(@RequestParam String username) throws NotAuthorizedUserException;

    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequest request) throws NoSpecifiedProductInWarehouseException, ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException, NoDeliveryFoundException;

    @PostMapping("/return")
    OrderDto productReturn(@RequestBody ProductReturnRequest request) throws NoOrderFoundException, ProductInShoppingCartLowQuantityInWarehouse;

    @PostMapping("/payment")
    OrderDto payment(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/payment/failed")
    OrderDto paymentFailed(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/delivery")
    OrderDto delivery(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/delivery/failed")
    OrderDto deliveryFailed(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/completed")
    OrderDto complete(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/calculate/total")
    OrderDto calculateTotalCost(@RequestBody String orderId) throws NoOrderFoundException, ProductNotFoundException, NotEnoughInfoInOrderToCalculateException;

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryCost(@RequestBody String orderId) throws NoOrderFoundException, NoDeliveryFoundException;

    @PostMapping("/assembly")
    OrderDto assembly(@RequestBody String orderId) throws NoOrderFoundException;

    @PostMapping("/assembly/failed")
    OrderDto assemblyFailed(@RequestBody String orderId) throws NoOrderFoundException;
}
