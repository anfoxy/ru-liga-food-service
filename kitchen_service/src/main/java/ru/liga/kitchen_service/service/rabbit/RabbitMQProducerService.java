package ru.liga.kitchen_service.service.rabbit;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);

}