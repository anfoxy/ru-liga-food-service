package ru.liga.notification_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.notification_service.service.impl.RabbitMQProducerServiceImpl;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;

    @RabbitListener(queues = "notification")
    public void notification(String message) {
        try {
            NotificationDto messageModel = readValueOrderDto(message);
            log.info("Received for NotificationDto message = " + messageModel.getMessage());
            rabbitMQProducerService.sendMessage(messageModel.getMessage(), messageModel.getRoutingKey());
        } catch (JsonProcessingException e) {
            log.error("customerQueue message = " + message);
            throw new RuntimeException(e);
        }
    }

    private NotificationDto readValueOrderDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        NotificationDto messageModel = objectMapper.readValue(message, NotificationDto.class);
        return messageModel;
    }

}