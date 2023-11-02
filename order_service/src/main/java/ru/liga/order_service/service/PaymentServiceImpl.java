package ru.liga.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.order_service.model.Order;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    public String pay(Order order) {
        log.info("an invoice has been issued for payment " + order.getId());
        return "link to payment " + order.getId();
    }

}
