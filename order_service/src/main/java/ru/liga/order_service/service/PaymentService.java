package ru.liga.order_service.service;

import ru.liga.order_service.model.Order;

public interface PaymentService {

    String pay(Order order);

}
