package ru.liga.delivery_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.delivery_service.exception.ServerException;


public interface LocalityDeterminant {

    public String getLocation(String coordinate);

}
