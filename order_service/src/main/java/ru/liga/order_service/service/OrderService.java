package ru.liga.order_service.service;

import org.springframework.stereotype.Service;
import ru.liga.order_service.dto.MenuItemDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrderDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import java.util.*;

@Service
public class OrderService {

    public OrdersResponseDto getAllOrders() throws ResourceNotFoundException {
        OrdersResponseDto orders = new OrdersResponseDto();
        if (orders == null) {
            throw new ResourceNotFoundException();
        }
        return orders;
    }

    public OrderDto getOrderById(Long id) throws ResourceNotFoundException {
        OrderDto order = new OrderDto();
        if (order == null) {
            throw new ResourceNotFoundException();
        }
        return order;
    }

    public OrderCreateResponseDto orderCreate(Long restaurantId, List<MenuItemDto> menuItems) throws ResourceNotFoundException {
        OrderCreateResponseDto order = new OrderCreateResponseDto();
        order.setId(1L);
        order.setSecretPaymentUrl("");
        order.setEstimatedTimeOfArrival("");
        if (restaurantId < 0) {
            throw new ResourceNotFoundException("restaurant not found for id:" + restaurantId + "");
        }
        return order;
    }

    public String orderUpdate(Long id, OrderDto order) throws ResourceNotFoundException {
        return "Заказ обновлен \n" + order;
    }

}
