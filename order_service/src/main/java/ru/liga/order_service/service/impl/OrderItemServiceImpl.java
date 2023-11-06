package ru.liga.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.OrderItemListMapper;
import ru.liga.commons.mapper.OrderItemMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.model.OrderItem;
import ru.liga.commons.model.RestaurantMenuItem;
import ru.liga.commons.repositories.OrderItemRepository;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.repositories.RestaurantMenuRepository;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.service.OrderItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final OrderItemMapper mapper;
    private final OrderItemListMapper listMapper;

    public OrderItemDto getOrderItemById(Long id) {
        return mapper
                .toDTO(orderItemRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new));
    }

    public List<OrderItemDto> getAllOrderItemByOrderId(Long id) {
        return listMapper
                .toDTOList(orderItemRepository
                        .findAllByOrder_Id(id));
    }

    public OrderItemDto createOrderItem(OrderItemCreateRequestDto orderItemDto) {
        if (orderItemDto.getOrder() == null || orderItemDto.getRestaurantMenuItem() == null) {
            throw new CreationException("Order == null or RestaurantMenuItem == null");
        }
        if (orderItemDto.getQuantity() < 0 ) {
            throw new CreationException("Quantity < 0");
        }

        Order order = orderRepository.findById(orderItemDto.getOrder()).orElseThrow(ResourceNotFoundException::new);

        RestaurantMenuItem restaurantMenuItem = restaurantMenuRepository
                .findById(orderItemDto.getRestaurantMenuItem())
                .orElseThrow(() -> new ResourceNotFoundException("not found RestaurantMenuItem"));

        if (restaurantMenuItem.getOrderItem() != null) {
            throw new CreationException("restaurantMenuItem is busy");
        }

        OrderItem orderItem = OrderItem
                .builder()
                .order(order)
                .quantity(orderItemDto.getQuantity())
                .restaurantMenuItem(restaurantMenuItem)
                .build();

        return mapper
                .toDTO(orderItemRepository
                        .save(orderItem));
    }

    public void createListOrderItem(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }

    public void deleteOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        orderItemRepository.delete(orderItem);
    }

    public OrderItemDto orderItemUpdate(Long id, OrderItemDto orderItemRequest) {
        OrderItem orderItem = mapper.toModel(getOrderItemById(id));
        mapper.updateOrderItemFromDto(orderItemRequest, orderItem);
        orderItem.setId(id);
        return mapper
                .toDTO(orderItemRepository
                        .save(orderItem));
    }

}