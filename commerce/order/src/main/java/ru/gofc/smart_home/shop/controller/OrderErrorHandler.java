package ru.gofc.smart_home.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gofc.smart_home.shop.exception.*;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class OrderErrorHandler {
    @ExceptionHandler(NotAuthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> unauthorized(NotAuthorizedUserException e) {
        log.warn(e.getMessage());
        return Map.of("NotAuthorizedUser:", e.getMessage());
    }

    @ExceptionHandler(NoSpecifiedProductInWarehouseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> noProduct(NoSpecifiedProductInWarehouseException e) {
        log.warn(e.getMessage());
        return Map.of("NoProduct:", e.getMessage());
    }

    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> lowQuantity(ProductInShoppingCartLowQuantityInWarehouse e) {
        log.warn(e.getMessage());
        return Map.of("LowQuantity:", e.getMessage());
    }

    @ExceptionHandler(NoOrderFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noOrder(NoOrderFoundException e) {
        log.warn(e.getMessage());
        return Map.of("NoOrder:", e.getMessage());
    }

    @ExceptionHandler(NoDeliveryFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noDelivery(NoDeliveryFoundException e) {
        log.warn(e.getMessage());
        return Map.of("NoDelivery:", e.getMessage());
    }

    @ExceptionHandler(NotEnoughInfoInOrderToCalculateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> notEnoughInfo(NotEnoughInfoInOrderToCalculateException e) {
        log.warn(e.getMessage());
        return Map.of("NotEnoughInfo:", e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> productNotFound(final ProductNotFoundException e) {
        log.warn(e.getMessage());
        return Map.of("Not Found: ", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> unknownException(Exception e) {
        log.warn(e.getMessage());
        return Map.of("Unknown:", e.getMessage());
    }
}
