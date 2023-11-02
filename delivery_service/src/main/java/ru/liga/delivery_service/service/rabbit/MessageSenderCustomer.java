package ru.liga.delivery_service.service.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.dto_model.OrderDto;

@Component
@RequiredArgsConstructor
public class MessageSenderCustomer {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderDto orderDto) {
        String messageModel = tryToSerialyzeMessageAsString(orderDto);
        rabbitMQProducerService.sendMessage(messageModel, "customer.order");
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
