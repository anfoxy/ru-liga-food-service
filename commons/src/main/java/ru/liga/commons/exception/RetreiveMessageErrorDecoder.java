package ru.liga.commons.exception;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        String message = message(exception.getMessage());
        switch (response.status()) {
            case 400:
                return new RequestException(message == null ? "Bad request" : message);
            case 404:
                return new ResourceNotFoundException(message == null ? "Not Found" : message);
        }

        return new RetryableException(
                response.status(),
                exception.getMessage(),
                response.request().httpMethod(),
                exception,
                null,
                response.request());
    }

    private String message(String input){
        String regex = "\"message\":\"(.*?)\"";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String messageText = matcher.group(1);
            return messageText;
        }
        return null;
    }
}