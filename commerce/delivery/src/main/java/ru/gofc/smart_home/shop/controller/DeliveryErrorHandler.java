package ru.gofc.smart_home.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gofc.smart_home.shop.exception.NoDeliveryFoundException;
import ru.gofc.smart_home.shop.exception.NoOrderFoundException;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class DeliveryErrorHandler {
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


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> unknownException(Exception e) {
        log.warn(e.getMessage());
        return Map.of("Unknown:", e.getMessage());
    }
}
