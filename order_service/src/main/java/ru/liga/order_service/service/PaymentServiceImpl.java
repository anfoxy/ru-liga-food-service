package ru.liga.order_service.service;

import lombok.extern.slf4j.Slf4j;
import ru.liga.order_service.model.Order;
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    public void pay(Order order) {
        log.info("an invoice has been issued for payment " + order.getId());
    }
}
