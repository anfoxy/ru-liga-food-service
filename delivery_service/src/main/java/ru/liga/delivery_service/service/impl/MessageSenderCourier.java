package ru.liga.delivery_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.DeliveryDto;

@Component
@RequiredArgsConstructor
public class MessageSenderCourier {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    public void sendMessage(DeliveryDto deliveryDto) {
        String messageModel = tryToSerialyzeMessageAsString(deliveryDto);
        rabbitMQProducerService.sendMessage(messageModel, "courier.order");
    }

    private String tryToSerialyzeMessageAsString(DeliveryDto messageModel) {
        String carInfoInLine = null;
        try {
            carInfoInLine = objectMapper.writeValueAsString(messageModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carInfoInLine;
    }

}
