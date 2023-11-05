package ru.liga.kitchen_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    @RabbitListener(queues = "restaurantQueue")
    public void createOrder(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from restaurant : " + messageModel);
        } catch (JsonProcessingException e) {
            log.error("restaurantQueue message = " + message);
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