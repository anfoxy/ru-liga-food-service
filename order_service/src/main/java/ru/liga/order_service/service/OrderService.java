package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.mapper.DtoMapper;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.repository.CustomerRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantRepository;
import ru.liga.commons.status.StatusOrders;
import java.time.ZonedDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final DtoMapper mapper;

    public List<Order> getAllOrderByStatus(StatusOrders status) {
        return orderRepository.findAllByStatus(status);
    }

    public List<Order> getAllOrderByCustomer(Long customerId) {
        return orderRepository.findAllByCustomer_Id(customerId);
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
        return orderRepository.save(orderResponse);
    }

    public Order updateOrderStatusById(Long id, String status) throws ResourceNotFoundException {
        Order order = getOrderById(id);
        try {
            order.setStatus(StatusOrders.valueOf(status));
        } catch (IllegalArgumentException t) {
            throw new ResourceNotFoundException("invalid status");
        }
        order = orderRepository.save(order);

        return order;
    }

    public OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto) throws ResourceNotFoundException, CreationException {
        Order order = orderCreateFromDto(orderDto);

        OrderCreateResponseDto orderCreateResponseDto = new OrderCreateResponseDto();
        orderCreateResponseDto.setId(order.getId());
        orderCreateResponseDto.setEstimatedTimeOfArrival("fff");
        orderCreateResponseDto.setSecretPaymentUrl("ddd");
        return orderCreateResponseDto;
    }

    private Order orderCreateFromDto(OrderCreateRequestDto orderDto) throws ResourceNotFoundException, CreationException {
        Order order = Order
                .builder()
                .customer(customerRepository
                        .findById(orderDto.getCustomer())
                        .orElseThrow(() -> new ResourceNotFoundException("not found Customer")))
                .restaurant(restaurantRepository
                        .findById(orderDto.getRestaurant())
                        .orElseThrow(() -> new ResourceNotFoundException("not found Restaurant")))
                .status(StatusOrders.CUSTOMER_CREATED)
                .timestamp(ZonedDateTime.now())
                .build();
        order.setOrderItems(orderItemService.getOrderItemListFromDto(order, orderDto.getOrderItems()));
        orderRepository.save(order);
        orderItemService.createListOrderItem(order.getOrderItems());
        return order;
    }

    //возможно убрать
    public OrdersResponseDto getAllOrders() throws ResourceNotFoundException {
        List<Order> orderArrayList = orderRepository.findAll();

        OrdersResponseDto orders = new OrdersResponseDto();
        orders.setOrders(orderArrayList);
        orders.setPageCount(orderArrayList.size());
        orders.setPageIndex(1);

        return orders;
    }

}
