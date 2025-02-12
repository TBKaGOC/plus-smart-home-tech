package ru.gofc.smart_home.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice("ru.gofc.smart_home")
public class EventErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> anyThrowable(Throwable e) {
        return Map.of("UnknownError", "Возникла неизвестная ошибка: " + e.getMessage());
    }
}
