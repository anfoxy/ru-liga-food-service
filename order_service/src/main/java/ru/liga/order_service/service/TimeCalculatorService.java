package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.model.Order;

import java.time.ZonedDateTime;

public interface TimeCalculatorService {
    ZonedDateTime calculator(Order order);

}
