package ru.liga.delivery_service.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MessageSenderRestaurant {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;

    public void sendMessage(String message) {
        rabbitMQProducerService.sendMessage(message, "restaurant.message");
    }

}
