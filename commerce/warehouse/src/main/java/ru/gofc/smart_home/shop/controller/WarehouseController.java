package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.api.WarehouseInterface;
import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.BookedProductsDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.exception.NoSpecifiedProductInWarehouseException;
import ru.gofc.smart_home.shop.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;
import ru.gofc.smart_home.shop.service.WarehouseService;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseController implements WarehouseInterface {
    final WarehouseService service;

    public void saveProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException {
        service.saveProduct(request);
    }

    public BookedProductsDto checkCart(ShoppingCartDto cart) throws ProductInShoppingCartLowQuantityInWarehouse {
        return service.checkCart(cart);
    }

    public void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException, ProductNotFoundException {
        service.addQuantity(request);
    }

    public AddressDto getAddress() {
        return service.getAddress();
    }
}
