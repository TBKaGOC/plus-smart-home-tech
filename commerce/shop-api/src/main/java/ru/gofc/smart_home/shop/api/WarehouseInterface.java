package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.BookedProductsDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.exception.NoSpecifiedProductInWarehouseException;
import ru.gofc.smart_home.shop.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;

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
}
