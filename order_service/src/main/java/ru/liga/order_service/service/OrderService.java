package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.mapper.OrderListMapper;
import ru.liga.order_service.mapper.OrderMapper;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.repository.CustomerRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.service.rabbit.MessageSender;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final MessageSender messageSender;
    private final OrderMapper mapper;
    private final OrderListMapper listMapper;
    private final PaymentService paymentService;
    private final TimeCalculatorService timeCalculator;

    public List<OrderDto> getAllOrderByStatus(StatusOrders status) {
        return listMapper
                .toDTOList(orderRepository
                        .findAllByStatus(status));
    }

    public List<OrderDto> getAllOrderByCustomer(Long customerId) {
        return listMapper
                .toDTOList(orderRepository
                        .findAllByCustomer_Id(customerId));
    }

    public OrderDto getOrderById(Long id) {
        return mapper
                .toDTO(getOrder(id));
    }

    private Order getOrder(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<OrderDto> getOrderByRestaurantID(Long restaurant_id) {
        return listMapper
                .toDTOList(orderRepository
                        .findAllByRestaurant_Id(restaurant_id));
    }

    public OrderDto orderUpdate(Long id, OrderDto orderRequest) {
        Order orderResponse = getOrder(id);
        mapper.updateOrderFromDto(orderRequest, orderResponse);
        orderResponse.setId(id);
        return mapper
                .toDTO(orderRepository
                        .save(orderResponse));
    }

    public OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto) {
        Order order = orderCreateFromDto(orderDto);
        OrderCreateResponseDto orderCreateResponseDto = new OrderCreateResponseDto();
        orderCreateResponseDto.setId(order.getId());
        orderCreateResponseDto.setEstimatedTimeOfArrival(timeCalculator.calculator(order));
        orderCreateResponseDto.setSecretPaymentUrl(paymentService.pay(order));
        return orderCreateResponseDto;
    }

    private Order orderCreateFromDto(OrderCreateRequestDto orderDto) {
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

    public OrderDto updateOrderStatusById(Long id, StatusOrders status) {
        Order order = getOrder(id);
        order.setStatus(status);
        order = orderRepository.save(order);

        OrderDto orderDto = mapper.toDTO(order);

        if (status.equals(StatusOrders.CUSTOMER_PAID)) {
            messageSender.sendOrder(orderDto);
        }
        return orderDto;
    }

}
