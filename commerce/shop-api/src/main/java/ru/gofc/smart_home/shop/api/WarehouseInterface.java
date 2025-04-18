package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.*;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.AssemblyProductsForOrderRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;
import ru.gofc.smart_home.shop.request.ShippedToDeliveryRequest;

import java.util.Map;

@RequestMapping("/api/v1/warehouse")
public interface WarehouseInterface {
    @PutMapping
    void saveProduct(@RequestBody NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException;

    @PostMapping("/check")
    BookedProductsDto checkCart(@RequestBody ShoppingCartDto cart) throws ProductInShoppingCartLowQuantityInWarehouse;

    @PostMapping("/add")
    void addQuantity(@RequestBody AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException, ProductNotFoundException;

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/assembly")
    BookedProductsDto assemblyProductsForOrder(@RequestBody AssemblyProductsForOrderRequest request) throws ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException;

    @PostMapping("/return")
    void acceptReturn(@RequestBody Map<String, Integer> products);

    @PostMapping("/shipped")
    void shippedToDelivery(ShippedToDeliveryRequest request) throws NoOrderFoundException;
}
