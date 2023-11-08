package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.commons.status.StatusOrders;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto getOrderById(UUID id);


    List<OrderDto> getAllOrderByCustomerID(UUID customerId);


    OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto);

    OrderDto orderUpdateCourierAndStatus(UUID id, OrderDto orderRequest);

    OrderDto updateOrderStatusById(UUID id, StatusOrders status);

}
