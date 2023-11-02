package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.mapper.OrderItemListMapper;
import ru.liga.order_service.mapper.OrderItemMapper;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.OrderItem;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.model.RestaurantMenuItem;
import ru.liga.order_service.repository.OrderItemRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantMenuRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

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
        Order order = orderRepository.findById(orderItemDto.getOrder()).orElseThrow(ResourceNotFoundException::new);
        return mapper
                .toDTO(orderItemRepository
                        .save(getOrderItemFromDto(order, orderItemDto)));
    }

    public void createListOrderItem(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }

    public OrderItem getOrderItemFromDto(Order order, OrderItemCreateRequestDto ItemDto) {
        if (ItemDto.getQuantity() < 0 || ItemDto.getRestaurantMenuItem() == null) {
            throw new CreationException("Quantity < 0 or RestaurantMenuItem == null");
        }

        RestaurantMenuItem restaurantMenuItem = restaurantMenuRepository
                .findById(ItemDto.getRestaurantMenuItem())
                .orElseThrow(() -> new ResourceNotFoundException("not found RestaurantMenuItem"));

        if (restaurantMenuItem.getOrderItem() != null) {
            throw new CreationException("restaurantMenuItem is busy");
        }

        OrderItem orderItem = OrderItem
                .builder()
                .order(order)
                .quantity(ItemDto.getQuantity())
                .restaurantMenuItem(restaurantMenuItem)
                .build();

        return orderItem;
    }

    public ArrayList<OrderItem> getOrderItemListFromDto(Order order, List<OrderItemCreateRequestDto> orderItemDtoList) {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        for (OrderItemCreateRequestDto ItemDto : orderItemDtoList) {
            orderItemArrayList.add(getOrderItemFromDto(order, ItemDto));
        }
        return orderItemArrayList;
    }

    public void deleteOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        orderItemRepository.delete(orderItem);
    }

    public OrderItemDto orderItemMenuUpdate(Long id, OrderItemDto orderItemRequest) {
        OrderItem orderItem = mapper.toModel(getOrderItemById(id));
        mapper.updateOrderItemFromDto(orderItemRequest, orderItem);
        orderItem.setId(id);
        return mapper
                .toDTO(orderItemRepository
                        .save(orderItem));
    }

}