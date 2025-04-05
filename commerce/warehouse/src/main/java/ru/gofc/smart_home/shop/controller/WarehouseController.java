package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.BookedProductsDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.exception.NoSpecifiedProductInWarehouseException;
import ru.gofc.smart_home.shop.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.gofc.smart_home.shop.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;
import ru.gofc.smart_home.shop.service.WarehouseService;

@RestController
@RequestMapping("/api/v1/warehouse")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseController {
    final WarehouseService service;

    @PutMapping
    public void saveProduct(@RequestBody NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException {
        service.saveProduct(request);
    }

    @PostMapping("/check")
    public BookedProductsDto checkCart(@RequestBody ShoppingCartDto cart) throws ProductInShoppingCartLowQuantityInWarehouse {
        return service.checkCart(cart);
    }

    @PostMapping("/add")
    public void addQuantity(@RequestBody AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException {
        service.addQuantity(request);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return service.getAddress();
    }
}
