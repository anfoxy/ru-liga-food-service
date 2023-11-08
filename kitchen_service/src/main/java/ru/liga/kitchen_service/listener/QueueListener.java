package ru.liga.kitchen_service.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    @RabbitListener(queues = "restaurantQueue")
    public void restaurantQueue(String message) {
        log.info("Received for restaurant: " + message);
    }

}