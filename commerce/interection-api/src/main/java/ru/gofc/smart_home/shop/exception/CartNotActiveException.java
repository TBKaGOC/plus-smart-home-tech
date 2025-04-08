package ru.gofc.smart_home.shop.exception;

public class CartNotActiveException extends Exception {
    public CartNotActiveException(String message) {
        super(message);
    }
}
