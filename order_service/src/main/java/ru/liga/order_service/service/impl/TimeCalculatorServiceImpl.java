package ru.liga.order_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.model.Order;
import ru.liga.order_service.service.TimeCalculatorService;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Помогает высчитывать примерное время доставки
 */
@Slf4j
@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    /**
     * Определяет примерное время доставки на основе количества пунктов заказа
     */
    public ZonedDateTime calculator(Order order) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Duration duration = Duration.ofMinutes(order.getOrderItems().size() * 20L);
        zonedDateTime = zonedDateTime.plus(duration);

        return zonedDateTime;
    }

}
