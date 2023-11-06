package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.commons.model.OrderItem;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;

import java.util.List;

public interface OrderItemService {


     OrderItemDto getOrderItemById(Long id);

     List<OrderItemDto> getAllOrderItemByOrderId(Long id);

     OrderItemDto createOrderItem(OrderItemCreateRequestDto orderItemDto);

     void createListOrderItem(List<OrderItem> orderItemList);

     void deleteOrderItemById(Long id);

     OrderItemDto orderItemUpdate(Long id, OrderItemDto orderItemRequest);

}