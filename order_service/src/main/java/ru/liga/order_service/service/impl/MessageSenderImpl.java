package ru.liga.order_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.service.MessageSender;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderDto orderDto) {
        String messageModel = tryToSerialyzeMessageAsString(orderDto);
        rabbitMQProducerService.sendMessage(messageModel, "restaurant.order");
    }

    private String tryToSerialyzeMessageAsString(OrderDto messageModel) {
        String carInfoInLine = null;
        try {
            carInfoInLine = objectMapper.writeValueAsString(messageModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carInfoInLine;
    }

}
