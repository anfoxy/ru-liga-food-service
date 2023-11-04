package ru.liga.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.order_service.model.Order;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Класс, который помогает высчитывать примерное время доставки
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
