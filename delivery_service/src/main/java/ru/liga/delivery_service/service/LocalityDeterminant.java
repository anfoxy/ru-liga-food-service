package ru.liga.delivery_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.exception.ServerException;


public interface LocalityDeterminant {

    public String getLocation(String coordinate);

}
