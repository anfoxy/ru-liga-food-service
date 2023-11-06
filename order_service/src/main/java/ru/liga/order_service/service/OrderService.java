package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.commons.status.StatusOrders;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Long id);

    List<OrderDto> getAllOrderByStatus(StatusOrders status);

    List<OrderDto> getAllOrderByCustomerID(Long customerId);

    List<OrderDto> getOrderByRestaurantID(Long restaurant_id);

    OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto);

    OrderDto orderUpdate(Long id, OrderDto orderRequest);

    OrderDto updateOrderStatusById(Long id, StatusOrders status);

}
