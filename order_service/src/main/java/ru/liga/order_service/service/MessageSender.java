package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;

public interface MessageSender {

    void sendOrder(OrderDto orderDto);

}
