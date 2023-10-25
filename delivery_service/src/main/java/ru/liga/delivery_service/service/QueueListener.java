package ru.liga.delivery_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.delivery_service.model.Order;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    @SneakyThrows
    @RabbitListener(queues = "queueNizhegorodsky")
    public void processQueueNizhegorodsky(String message) {
        Order messageModel = readValue(message);
        log.info("Received from queueNizhegorodsky : " +  messageModel.getId());
        log.info("Нижегородский район");
    }

    @SneakyThrows
    @RabbitListener(queues = "queuePrioksky")
    public void processQueuePrioksky(String message) {
        Order messageModel = readValue(message);
        log.info("Received from queuePrioksky : " +  messageModel.getId());
        log.info("Приокский район");
    }

    @SneakyThrows
    @RabbitListener(queues = "queueSovetsky")
    public void processQueueSovetsky(String message) {
        Order messageModel = readValue(message);
        log.info("Received from queueSovetsky : " +  messageModel.getId());
        log.info("Советский район");
    }

    private Order readValue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Order messageModel = objectMapper.readValue(message, Order.class);
        return messageModel;
    }
}