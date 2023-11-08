package ru.liga.delivery_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;

public interface PaymentService {

    void refund(OrderDto order);

}
