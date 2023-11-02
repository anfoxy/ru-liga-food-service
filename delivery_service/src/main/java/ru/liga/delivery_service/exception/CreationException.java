package ru.liga.delivery_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreationException extends RuntimeException {

    public CreationException() {
        super("Failed to create");
    }

    public CreationException(String message) {
        super(message);
    }

}