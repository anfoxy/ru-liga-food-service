package ru.liga.delivery_service.service;

import org.springframework.stereotype.Service;

@Service
public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    public double calculator(double distance) {
        return distance * 0.5;
    }

}
