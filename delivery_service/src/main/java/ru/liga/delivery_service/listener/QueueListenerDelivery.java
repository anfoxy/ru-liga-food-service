package ru.liga.delivery_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.DeliveryDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListenerDelivery {

    @RabbitListener(queues = "courierQueue")
    public void createOrder(String message) {
        try {
            DeliveryDto messageModel = readValue(message);
            log.info("Received from courier : " + messageModel);
        } catch (JsonProcessingException e) {
            log.error("courierQueue message = " + message);
            throw new RuntimeException(e);
        }
    }

    private DeliveryDto readValue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        DeliveryDto messageModel = objectMapper.readValue(message, DeliveryDto.class);
        return messageModel;
    }

}