package ru.liga.kitchen_service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.kitchen_service.service.impl.RabbitMQProducerServiceImpl;

@Component
@RequiredArgsConstructor
public class MessageSenderRestaurant {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;

    public void sendMessage(String message) {
        rabbitMQProducerService.sendMessage(message, "restaurant.message");
    }

}
