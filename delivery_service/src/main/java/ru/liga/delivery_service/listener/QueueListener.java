package ru.liga.delivery_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.delivery_service.feign.RestaurantFeign;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.delivery_service.service.impl.MessageSenderCourier;
import ru.liga.delivery_service.service.impl.OrderToDeliveryConverter;


@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierService courierService;
    private final MessageSenderCourier messageSender;
    private final OrderToDeliveryConverter toDeliveryConverter;
    private final RestaurantFeign restaurantFeign;

    @RabbitListener(queues = "queueNizhegorodsky")
    public void processQueueNizhegorodsky(String message) {
        try {
            OrderDto messageModel = readValue(message);
            log.info("Received from queueNizhegorodsky : " + messageModel.getId());
            setCourierForDelivery(messageModel, "Nizhegorodsky");
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
            setCourierForDelivery(messageModel, "Prioksky");
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
            setCourierForDelivery(messageModel, "Sovetsky");
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

    public void setCourierForDelivery(OrderDto orderDto, String location) {
        log.info("CourierService: search for the nearest courier in area " + location);
        CourierDto courierDto = courierService.getClosestCourier(orderDto.getRestaurant().getAddress(), location);

        if (courierDto != null) {
            log.info("CourierService: The nearest courier has been found: " + courierDto);
            messageSender.sendMessage(toDeliveryConverter.deliveryDtoCreateFromOrder(orderDto, courierDto));
        } else {
            ConfirmCourierDto confirmCourierDto = ConfirmCourierDto
                    .builder()
                    .status("Not found")
                    .build();

            restaurantFeign.pickingOrderById(confirmCourierDto);
            log.info("CourierService: The nearest courier was not found");
        }
    }
}