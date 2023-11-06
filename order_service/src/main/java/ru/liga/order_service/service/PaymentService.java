package ru.liga.order_service.service;

import ru.liga.commons.model.Order;

public interface PaymentService {

    String pay(Order order);

}
