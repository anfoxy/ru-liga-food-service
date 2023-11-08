package ru.liga.delivery_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.delivery_service.rabbit.MessageSender;
import ru.liga.delivery_service.service.CourierService;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierService courierService;
    private final MessageSender messageSender;


    @RabbitListener(queues = "courierQueue")
    public void courierQueue(String message) {
        log.info("Received for courier: " + message);
    }

    @RabbitListener(queues = "queueNizhegorodsky")
    public void queueNizhegorodsky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queueNizhegorodsky: " + messageModel.getId());
            setCourierForDelivery(messageModel, "Nizhegorodsky");
        } catch (JsonProcessingException e) {
            log.error("QueueNizhegorodsky message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "queuePrioksky")
    public void queuePrioksky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queuePrioksky: " + messageModel.getId());
            setCourierForDelivery(messageModel, "Prioksky");
        } catch (JsonProcessingException e) {
            log.error("QueuePrioksky message = " + message);
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "queueSovetsky")
    public void queueSovetsky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queueSovetsky: " + messageModel.getId());
            setCourierForDelivery(messageModel, "Sovetsky");
        } catch (JsonProcessingException e) {
            log.error("QueueSovetsky message = " + message);
            throw new RuntimeException(e);
        }
    }

    private OrderDto readValue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderDto messageModel = objectMapper.readValue(message, OrderDto.class);
        return messageModel;
    }

    public void setCourierForDelivery(OrderDto orderDto, String location) {
        log.info("CourierService: search for the nearest courier in area " + location);
        CourierDto courierDto = courierService.getClosestCourier(orderDto.getRestaurant().getAddress(), location);
        if (courierDto != null) {
            log.info("CourierService: The nearest courier has been found: " + courierDto);
            messageSender.sendMessage(NotificationDto
                    .builder()
                    .routingKey("courier.message")
                    .message("A new order " + orderDto.getId() +
                            " has been found for Courier " + courierDto.getId())
                    .build());
        } else {
            messageSender.sendMessage(NotificationDto
                    .builder()
                    .routingKey("restaurant.message")
                    .message("At the request of the restorer with the ID 1," +
                            " no couriers were found available")
                    .build());
            log.info("CourierService: The nearest courier was not found");
        }
    }
}