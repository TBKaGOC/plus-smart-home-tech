package ru.gofc.smart_home.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gofc.smart_home.shop.exception.*;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(NoProductsInShoppingCartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> noInCart(final NoProductsInShoppingCartException e) {
        log.warn(e.getMessage());
        return Map.of("Not Found: ", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> argumentNotValid(final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        return Map.of("Not Valid: ", e.getMessage());
    }

    @ExceptionHandler(CartNotActiveException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public Map<String, String> notActive(final CartNotActiveException e) {
        log.warn(e.getMessage());
        return Map.of("Not Active: ", e.getMessage());
    }

    @ExceptionHandler(NotAuthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> unauthorized(final NotAuthorizedUserException e) {
        log.warn(e.getMessage());
        return Map.of("Unauthorized: ", e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final ProductNotFoundException e) {
        log.warn(e.getMessage());
        return Map.of("Not Found: ", e.getMessage());
    }

    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> lowQuantity(final ProductInShoppingCartLowQuantityInWarehouse e) {
        log.warn(e.getMessage());
        return Map.of("Low Quantity: ", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> unknownException(Exception e) {
        log.warn(e.getMessage());
        return Map.of("Unknown: ", e.getMessage());
    }
}
