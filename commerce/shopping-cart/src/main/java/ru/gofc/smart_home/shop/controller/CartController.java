package ru.gofc.smart_home.shop.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.ChangeProductQuantityRequest;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.service.CartService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {
    final CartService service;

    @GetMapping
    public ShoppingCartDto findCartByOwner(@RequestParam String username) throws NotAuthorizedUserException {
        return service.findCartByOwner(username);
    }

    @PutMapping
    public ShoppingCartDto saveProduct (@RequestParam String username, @RequestBody Map<String, Integer> products) throws NotAuthorizedUserException, CartNotActiveException, ProductInShoppingCartLowQuantityInWarehouse {
        return service.saveProduct(username, products);
    }

    @DeleteMapping
    public void deactivateCart(@RequestParam String username) throws NotAuthorizedUserException {
        service.deactivateCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProducts(@RequestParam String username, @RequestBody Map<String, Integer> products) throws NoProductsInShoppingCartException, NotAuthorizedUserException {
        return service.removeProducts(username, products);
    }

    @PostMapping("/change-quantity")
    public ProductDto changeQuantity(@RequestParam String username, @RequestBody @Valid ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException, NotAuthorizedUserException, ProductNotFoundException, ProductInShoppingCartLowQuantityInWarehouse {
        return service.changeQuantity(username, request);
    }
}
