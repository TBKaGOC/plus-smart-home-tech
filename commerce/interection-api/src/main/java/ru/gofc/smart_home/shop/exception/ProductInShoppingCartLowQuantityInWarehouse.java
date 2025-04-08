package ru.gofc.smart_home.shop.exception;

public class ProductInShoppingCartLowQuantityInWarehouse extends Exception {
    public ProductInShoppingCartLowQuantityInWarehouse(String message) {
        super(message);
    }
}
