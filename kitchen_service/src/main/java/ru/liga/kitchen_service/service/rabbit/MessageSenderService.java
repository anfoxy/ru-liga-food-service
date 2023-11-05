package ru.liga.kitchen_service.service.rabbit;


import ru.liga.commons.dto.dto_model.OrderDto;

public interface MessageSenderService {

    void sendOrder(OrderDto message);

}