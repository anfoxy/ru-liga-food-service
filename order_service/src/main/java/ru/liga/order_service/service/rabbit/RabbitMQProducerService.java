package ru.liga.order_service.service.rabbit;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);

}