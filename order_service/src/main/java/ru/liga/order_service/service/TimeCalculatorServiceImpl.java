package ru.liga.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.order_service.model.Order;

import java.time.Duration;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    public ZonedDateTime calculator(Order order) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Duration duration = Duration.ofMinutes(order.getOrderItems().size() * 20L);
        zonedDateTime = zonedDateTime.plus(duration);

        return zonedDateTime;
    }

}
