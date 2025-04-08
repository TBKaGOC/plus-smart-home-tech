package ru.gofc.smart_home.shop.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.api.CartInterface;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.ChangeProductQuantityRequest;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.service.CartService;

import java.util.Map;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController implements CartInterface {
    final CartService service;

    public ShoppingCartDto findCartByOwner(String username) throws NotAuthorizedUserException {
        return service.findCartByOwner(username);
    }

    public ShoppingCartDto saveProduct (String username, Map<String, Integer> products) throws NotAuthorizedUserException, CartNotActiveException, ProductInShoppingCartLowQuantityInWarehouse {
        return service.saveProduct(username, products);
    }

    public void deactivateCart(String username) throws NotAuthorizedUserException {
        service.deactivateCart(username);
    }

    public ShoppingCartDto removeProducts(String username, Map<String, Integer> products) throws NoProductsInShoppingCartException, NotAuthorizedUserException {
        return service.removeProducts(username, products);
    }

    public ProductDto changeQuantity(String username, @Valid ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException, NotAuthorizedUserException, ProductNotFoundException, ProductInShoppingCartLowQuantityInWarehouse {
        return service.changeQuantity(username, request);
    }
}
