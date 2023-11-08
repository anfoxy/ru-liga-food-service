package ru.liga.delivery_service.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.delivery_service.service.impl.RabbitMQProducerServiceImpl;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    public void sendMessage(NotificationDto notificationDto) {
        String messageModel = tryToSerialyzeMessageAsString(notificationDto);
        rabbitMQProducerService.sendMessage(messageModel, "notification.message");
    }

    private String tryToSerialyzeMessageAsString(NotificationDto messageModel) {
        String carInfoInLine = null;
        try {
            carInfoInLine = objectMapper.writeValueAsString(messageModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carInfoInLine;
    }

}
