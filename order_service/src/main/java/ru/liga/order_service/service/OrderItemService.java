package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.model.OrderItem;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.exception.ResourceNotFoundException;

import ru.liga.order_service.repository.OrderItemRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantMenuRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;

    public OrderItem getOrderItemById(Long id) throws ResourceNotFoundException {
        return orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<OrderItem> getOrderItemByOrderId(Long id) {
        return orderItemRepository.findAllByOrder_Id(id);
    }

    public OrderItem createOrderItem(OrderItemCreateRequestDto orderItemDto) throws ResourceNotFoundException {
        if (orderItemDto.getOrder() == null || orderItemDto.getRestaurantMenuItem() == null) {
            throw new ResourceNotFoundException("Order == null or RestaurantMenuItem == null");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setOrder(orderRepository
                .findById(orderItemDto.getOrder())
                .orElseThrow(() -> new ResourceNotFoundException("not found Order")));
        orderItem.setRestaurantMenuItem(restaurantMenuRepository
                .findById(orderItemDto.getRestaurantMenuItem())
                .orElseThrow(() -> new ResourceNotFoundException("not found RestaurantMenuItem")));
        orderItem.setId(null);
        return orderItemRepository.save(orderItem);
    }

    public void createListOrderItem(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }

    public void deleteOrderItemById(Long id) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        orderItemRepository.delete(orderItem);
    }
}