package ru.liga.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.OrderListMapper;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.model.OrderItem;
import ru.liga.commons.model.RestaurantMenuItem;
import ru.liga.commons.repositories.CustomerRepository;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.repositories.RestaurantMenuRepository;
import ru.liga.commons.repositories.RestaurantRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.service.MessageSender;
import ru.liga.order_service.service.OrderItemService;
import ru.liga.order_service.service.OrderService;
import ru.liga.order_service.service.PaymentService;
import ru.liga.order_service.service.TimeCalculatorService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
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

    public List<OrderDto> getAllOrderByCustomerID(Long customerId) {
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
        log.info("OrderCreateResponseDto input data: " + orderDto);
        Order order = orderCreateFromDto(orderDto);
        log.info("the order was saved: " + order);
        OrderCreateResponseDto orderCreateResponseDto = new OrderCreateResponseDto();
        orderCreateResponseDto.setId(order.getId());
        orderCreateResponseDto.setEstimatedTimeOfArrival(timeCalculator.calculator(order));
        orderCreateResponseDto.setSecretPaymentUrl(paymentService.pay(order));
        log.info("response to the customer's request to create an order: " + orderCreateResponseDto);
        return orderCreateResponseDto;
    }

    private Order orderCreateFromDto(OrderCreateRequestDto orderDto) {
        Order order = Order
                .builder()
                .customer(customerRepository
                        .findById(orderDto.getCustomer())
                        .orElseThrow(() -> {
                            log.error("not found Customer by id = " + orderDto.getCustomer());
                            return new ResourceNotFoundException("not found Customer");
                        }))
                .restaurant(restaurantRepository
                        .findById(orderDto.getRestaurant())
                        .orElseThrow(() -> {
                            log.error("not found Restaurant by id = " + orderDto.getCustomer());
                            return new ResourceNotFoundException("not found Restaurant");
                        }))
                .status(StatusOrders.CUSTOMER_CREATED)
                .timestamp(ZonedDateTime.now())
                .build();
        order.setOrderItems(getOrderItemListFromDto(order, orderDto.getOrderItems()));
        log.info("order created: " + order);
        orderRepository.save(order);
        orderItemService.createListOrderItem(order.getOrderItems());
        log.info("the order is saved: " + order);

        return order;
    }

    private ArrayList<OrderItem> getOrderItemListFromDto(Order order, List<OrderItemCreateRequestDto> orderItemDtoList) {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        for (OrderItemCreateRequestDto ItemDto : orderItemDtoList) {
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
            orderItemArrayList.add(orderItem);
        }
        return orderItemArrayList;
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
