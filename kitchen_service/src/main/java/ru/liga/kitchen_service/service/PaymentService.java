package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;

public interface PaymentService {

    boolean refund(OrderDto order);

}
