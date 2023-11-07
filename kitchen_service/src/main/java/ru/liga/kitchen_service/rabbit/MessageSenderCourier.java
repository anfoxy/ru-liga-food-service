package ru.liga.kitchen_service.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.ServerException;
import ru.liga.commons.util.LocalityDeterminant;
import ru.liga.kitchen_service.service.impl.RabbitMQProducerServiceImpl;

@Component
@RequiredArgsConstructor
public class MessageSenderCourier {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;
    private final LocalityDeterminant localityDeterminant;

    public void sendOrder(OrderDto orderDto) {
        String messageModel = tryToSerialyzeMessageAsString(orderDto);
        switch (localityDeterminant.getLocation(orderDto.getCustomer().getAddress())) {
            case "Sovetsky":
                rabbitMQProducerService.sendMessage(messageModel, "district.sovetsky");
                break;
            case "Nizhegorodsky":
                rabbitMQProducerService.sendMessage(messageModel, "district.nizhegorodsky");
                break;
            case "Prioksky":
                rabbitMQProducerService.sendMessage(messageModel, "district.prioksky");
                break;
        }
    }


    private String tryToSerialyzeMessageAsString(OrderDto messageModel) {
        String carInfoInLine = null;
        try {
            carInfoInLine = objectMapper.writeValueAsString(messageModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException();
        }
        return carInfoInLine;
    }

}
