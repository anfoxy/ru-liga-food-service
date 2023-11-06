package ru.liga.notification_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.dto_model.OrderDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    @RabbitListener(queues = "customerQueue")
    public void customerQueue(String message) {
        try {
            OrderDto messageModel = readValueOrderDto(message);
            log.info("Received message for customer by id " + messageModel.getCustomer().getId());
            log.info("Received for customer message = " + messageModel);
        } catch (JsonProcessingException e) {
            log.error("customerQueue message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "restaurantQueue")
    public void restaurantQueue(String message) {
        try {
            OrderDto messageModel = readValueOrderDto(message);
            log.info("Received message for restaurant by id " + messageModel.getRestaurant().getId());
            log.info("Received for customer message = " + messageModel);
        } catch (JsonProcessingException e) {
            log.error("restaurantQueue message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "restaurantMessageQueue")
    public void restaurantMessageQueue(String message) {
        log.info("Received for restaurant message = " + message);
    }

    @RabbitListener(queues = "courierQueue")
    public void createOrder(String message) {
        try {
            DeliveryDto messageModel = readValueDeliveryDto(message);
            log.info("Received message for courier by id " + messageModel.getCourierId());
            log.info("Received for customer message = " + messageModel);
        } catch (JsonProcessingException e) {
            log.error("courierQueue message = " + message);
            throw new RuntimeException(e);
        }
    }

    private OrderDto readValueOrderDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderDto messageModel = objectMapper.readValue(message, OrderDto.class);
        return messageModel;
    }

    private DeliveryDto readValueDeliveryDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        DeliveryDto messageModel = objectMapper.readValue(message, DeliveryDto.class);
        return messageModel;
    }

}