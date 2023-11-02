package ru.liga.kitchen_service.service.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.kitchen_service.exception.ServerException;

@Component
@RequiredArgsConstructor
public class MessageSenderCourier implements MessageSenderService {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderDto orderDto) {
        String messageModel = tryToSerialyzeMessageAsString(orderDto);
        switch (findDistrict(orderDto.getCustomer().getAddress())) {
            case "Советский":
                rabbitMQProducerService.sendMessage(messageModel, "district.sovetsky");
                break;
            case "Нижегородский":
                rabbitMQProducerService.sendMessage(messageModel, "district.nizhegorodsky");
                break;
            case "Приокский":
                rabbitMQProducerService.sendMessage(messageModel, "district.prioksky");
                break;
        }
    }

    private String findDistrict(String address) {
        double latitudeCoordinate, longitudeCoordinate;
        try {
            latitudeCoordinate = Double.parseDouble(address
                    .substring(1, address.indexOf(',')));
            longitudeCoordinate = Double.parseDouble(address
                    .substring(address.indexOf(',') + 1, address.length() - 1));
        } catch (NumberFormatException numberFormatException) {
            throw new ServerException();
        }
        if (longitudeCoordinate - latitudeCoordinate <= 0) {
            return "Советский";
        } else if (longitudeCoordinate - latitudeCoordinate >= 20) {
            return "Нижегородский";
        } else {
            return "Приокский";
        }
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
