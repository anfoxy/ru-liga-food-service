package ru.liga.delivery_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.delivery_service.service.CourierService;


@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierService courierService;

    @RabbitListener(queues = "queueNizhegorodsky")
    public void processQueueNizhegorodsky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queueNizhegorodsky : " + messageModel.getId());
            courierService.setCourierForDelivery(messageModel, "Nizhegorodsky");
        } catch (JsonProcessingException e) {
            log.error("queueNizhegorodsky message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "queuePrioksky")
    public void processQueuePrioksky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queuePrioksky : " + messageModel.getId());
            courierService.setCourierForDelivery(messageModel, "Prioksky");
        } catch (JsonProcessingException e) {
            log.error("queuePrioksky message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "queueSovetsky")
    public void processQueueSovetsky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queueSovetsky : " + messageModel.getId());
            courierService.setCourierForDelivery(messageModel, "Sovetsky");
        } catch (JsonProcessingException e) {
            log.error("queueSovetsky message = " + message);
            throw new RuntimeException(e);
        }
    }

    private OrderDto readValue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderDto messageModel = objectMapper.readValue(message, OrderDto.class);
        return messageModel;
    }

}