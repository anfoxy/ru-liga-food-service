package ru.liga.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestException extends RuntimeException {

    public RequestException() {
        super("bad request");
    }

    public RequestException(String message) {
        super(message);
    }

}