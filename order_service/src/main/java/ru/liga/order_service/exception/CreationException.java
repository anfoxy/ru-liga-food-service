package ru.liga.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CreationException extends Exception {

    public CreationException() {
        super("Failed to create");
    }

    public CreationException(String message) {
        super(message);
    }

}