package ru.gofc.smart_home.shop.exception;

public class NotAuthorizedUserException extends Exception {
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
