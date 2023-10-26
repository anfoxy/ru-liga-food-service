package ru.liga.order_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.mapper.DtoMapper;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.OrderItem;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.repository.CustomerRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantMenuRepository;
import ru.liga.order_service.repository.RestaurantRepository;
import ru.liga.commons.status.StatusOrders;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final DtoMapper mapper;

    public OrdersResponseDto getAllOrders() throws ResourceNotFoundException {
        List<Order> orderArrayList = orderRepository.findAll();

        OrdersResponseDto orders = new OrdersResponseDto();
        orders.setOrders(orderArrayList);
        orders.setPageCount(orderArrayList.size());
        orders.setPageIndex(1);

        return orders;
    }


    public Order getOrderById(Long id) throws ResourceNotFoundException {
        return orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Order> getOrderByRestaurantID(Long restaurant_id) {
        return orderRepository.findAllByRestaurant_Id(restaurant_id);
    }

    public Order orderUpdate(Long id, Order orderRequest) throws ResourceNotFoundException {
        Order orderResponse = getOrderById(id);
        mapper.updateOrderFromDto(orderRequest, orderResponse);
        orderResponse.setId(id);
        orderRepository.save(orderResponse);
        return orderResponse;
    }

    public OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto) throws ResourceNotFoundException {
        Order order = orderCreateFromDto(orderDto);
        OrderCreateResponseDto orderCreateResponseDto = new OrderCreateResponseDto();
        orderCreateResponseDto.setId(order.getId());
        orderCreateResponseDto.setEstimatedTimeOfArrival("fff");
        orderCreateResponseDto.setSecretPaymentUrl("ddd");
        return orderCreateResponseDto;
    }

    private Order orderCreateFromDto(OrderCreateRequestDto orderDto) throws ResourceNotFoundException {
        Order order = new Order();
        order.setCustomer(customerRepository
                .findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("not found Customer")));
        order.setRestaurant(restaurantRepository
                .findById(orderDto.getRestaurant())
                .orElseThrow(() -> new ResourceNotFoundException("not found Restaurant")));
        order.setStatus(StatusOrders.CUSTOMER_CREATED);
        order.setTimestamp(ZonedDateTime.now());
        order.setOrderItems(setOrderItem(order,orderDto.getOrderItems()));
        orderRepository.save(order);
        orderItemService.createListOrderItem(order.getOrderItems());
        return order;
    }

    private ArrayList<OrderItem> setOrderItem(Order order, List<OrderItemCreateRequestDto> orderItemDtoList) throws ResourceNotFoundException {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        for (OrderItemCreateRequestDto ItemDto : orderItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(ItemDto.getQuantity());
            orderItem.setRestaurantMenuItem(restaurantMenuRepository
                    .findById(ItemDto.getRestaurantMenuItem())
                    .orElseThrow(() -> new ResourceNotFoundException("not found RestaurantMenuItem")));
            orderItemArrayList.add(orderItem);
        }
        return orderItemArrayList;
    }


    public Order updateOrderStatusById(Long id, String status) throws ResourceNotFoundException {
       Order order = getOrderById(id);
       try {
           order.setStatus(StatusOrders.valueOf(status));
       } catch (IllegalArgumentException t){
           throw new ResourceNotFoundException("invalid status");
       }
       return orderRepository.save(order);
    }

    public List<Order> getAllOrderByStatus(StatusOrders status) {
        return orderRepository.findAllByStatus(status);
    }
}
