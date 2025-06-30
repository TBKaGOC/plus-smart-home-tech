package ru.gofc.smart_home.shop.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.api.WarehouseInterface;
import ru.gofc.smart_home.shop.dto.*;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.AssemblyProductsForOrderRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;
import ru.gofc.smart_home.shop.request.ShippedToDeliveryRequest;
import ru.gofc.smart_home.shop.service.WarehouseService;

import java.util.Map;

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

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request) throws ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException {
        return service.assembly(request);
    }

    @Override
    public void acceptReturn(Map<String, Integer> products) {
        service.acceptReturn(products);
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) throws NoOrderFoundException {
        service.shippedToDelivery(request);
    }
}
