package ru.gofc.smart_home.shop.api;

import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.ChangeProductQuantityRequest;

import java.util.Map;

@RequestMapping("/api/v1/shopping-cart")
public interface CartInterface {
    @GetMapping
    ShoppingCartDto findCartByOwner(@RequestParam String username) throws NotAuthorizedUserException;

    @PutMapping
    ShoppingCartDto saveProduct (@RequestParam String username, @RequestBody Map<String, Integer> products) throws NotAuthorizedUserException, CartNotActiveException, ProductInShoppingCartLowQuantityInWarehouse;

    @DeleteMapping
    void deactivateCart(@RequestParam String username) throws NotAuthorizedUserException;

    @PostMapping("/remove")
    ShoppingCartDto removeProducts(@RequestParam String username, @RequestBody Map<String, Integer> products) throws NoProductsInShoppingCartException, NotAuthorizedUserException;

    @PostMapping("/change-quantity")
    ProductDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request) throws NoProductsInShoppingCartException, NotAuthorizedUserException, ProductNotFoundException, ProductInShoppingCartLowQuantityInWarehouse;
}
