package ru.liga.delivery_service.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.delivery_service.service.DeliveryCostCalculator;

@Service
public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    public double calculator(double distance) {
        return distance * 0.5;
    }

}
