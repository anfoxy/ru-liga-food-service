package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.mapper.DtoMapper;
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
    private final DtoMapper mapper;

    public OrderItem getOrderItemById(Long id) throws ResourceNotFoundException {
        return orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<OrderItem> getAllOrderItemByOrderId(Long id) {
        return orderItemRepository.findAllByOrder_Id(id);
    }

    public OrderItem createOrderItem(OrderItemCreateRequestDto orderItemDto) throws ResourceNotFoundException, CreationException {
        if (orderItemDto.getOrder() == null || orderItemDto.getRestaurantMenuItem() == null) {
            throw new CreationException("Order == null or RestaurantMenuItem == null");
        }
        Order order = orderRepository.findById(orderItemDto.getOrder()).orElseThrow(ResourceNotFoundException::new);
        return orderItemRepository.save(getOrderItemFromDto(order, orderItemDto));
    }

    public void createListOrderItem(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }

    public OrderItem getOrderItemFromDto(Order order, OrderItemCreateRequestDto ItemDto) throws ResourceNotFoundException, CreationException {
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

    public ArrayList<OrderItem> getOrderItemListFromDto(Order order, List<OrderItemCreateRequestDto> orderItemDtoList) throws ResourceNotFoundException, CreationException {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        for (OrderItemCreateRequestDto ItemDto : orderItemDtoList) {
            orderItemArrayList.add(getOrderItemFromDto(order, ItemDto));
        }
        return orderItemArrayList;
    }

    public void deleteOrderItemById(Long id) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        orderItemRepository.delete(orderItem);
    }

    public OrderItem orderItemMenuUpdate(Long id, OrderItem orderItemRequest) throws ResourceNotFoundException {
        OrderItem orderItem = getOrderItemById(id);
        mapper.updateOrderItemFromDto(orderItemRequest, orderItem);
        orderItem.setId(id);
        return orderItemRepository.save(orderItem);
    }

}