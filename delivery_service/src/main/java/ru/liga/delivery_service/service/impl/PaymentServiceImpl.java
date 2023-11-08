package ru.liga.delivery_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.delivery_service.service.PaymentService;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    public void refund(OrderDto order) {
        log.info("Refund of funds  " + order);
    }

}
