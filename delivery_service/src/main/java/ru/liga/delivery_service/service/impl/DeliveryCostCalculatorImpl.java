package ru.liga.delivery_service.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.delivery_service.service.DeliveryCostCalculator;

/**
 * Класс, который помогает высчитывать оплату курьеру за доставку
 */
@Service
public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    public double calculator(double distance) {
        return distance * 0.5;
    }

}
