package ru.liga.order_service.service;

import ru.liga.commons.model.Order;

import java.time.ZonedDateTime;

public interface TimeCalculatorService {
    ZonedDateTime calculator(Order order);

}
