package ru.gofc.smart_home.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;
import ru.gofc.smart_home.shop.exception.NoSpecifiedProductInWarehouseException;
import ru.gofc.smart_home.shop.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.gofc.smart_home.shop.exception.SpecifiedProductAlreadyInWarehouseException;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(SpecifiedProductAlreadyInWarehouseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> alreadyIn(SpecifiedProductAlreadyInWarehouseException e) {
        log.warn(e.getMessage());
        return Map.of("AlreadyInWarehouse: ", e.getMessage());
    }

    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> lowQuantity(ProductInShoppingCartLowQuantityInWarehouse e) {
        log.warn(e.getMessage());
        return Map.of("LowQuantity: ", e.getMessage());
    }

    @ExceptionHandler(NoSpecifiedProductInWarehouseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> noProduct(NoSpecifiedProductInWarehouseException e) {
        log.warn(e.getMessage());
        return Map.of("NoProduct: ", e.getMessage());
    }

    @ExceptionHandler(NoOrderFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noOrder(NoOrderFoundException e) {
        log.warn(e.getMessage());
        return Map.of("NoOrder:", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> unknownException(Exception e) {
        log.warn(e.getMessage());
        return Map.of("Unknown: ", e.getMessage());
    }
}
