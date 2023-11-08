package ru.liga.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.RequestException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.OrderListMapper;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.model.OrderItem;
import ru.liga.commons.model.RestaurantMenuItem;
import ru.liga.commons.repositories.CourierRepository;
import ru.liga.commons.repositories.CustomerRepository;
import ru.liga.commons.repositories.OrderItemRepository;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.repositories.RestaurantMenuRepository;
import ru.liga.commons.repositories.RestaurantRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.rabbit.MessageSender;
import ru.liga.order_service.service.OrderService;
import ru.liga.order_service.service.PaymentService;
import ru.liga.order_service.service.TimeCalculatorService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final CustomerRepository customerRepository;
    private final CourierRepository courierRepository;
    private final MessageSender messageSender;
    private final OrderMapper mapper;
    private final OrderListMapper listMapper;
    private final PaymentService paymentService;
    private final TimeCalculatorService timeCalculator;

    public List<OrderDto> getAllOrderByCustomerID(UUID customerId) {
        return listMapper
                .toDTOList(orderRepository
                        .findAllByCustomer_Id(customerId));
    }

    public OrderDto getOrderById(UUID id) {
        return mapper
                .toDTO(getOrder(id));
    }

    private Order getOrder(UUID id) {
        return orderRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }


    public OrderDto orderUpdateCourierAndStatus(UUID id, OrderDto orderRequest) {
        log.info("OrderUpdate input data: " + orderRequest);
        Order orderResponse = getOrder(id);
        if (!checkNewStatus(orderResponse.getStatus(), orderRequest.getStatus())) {
            throw new RequestException("The order status " + orderResponse.getStatus().toString()
                    + " cannot be changed to the specified");
        }
        log.info("Current order by id " + id + ": " + orderResponse);
        orderResponse.setStatus(orderRequest.getStatus());
        orderResponse.setCourier(courierRepository
                .findById(orderRequest.getCourier().getId())
                .orElseThrow(() -> {
                    log.error("Not found courier by id = " + orderResponse.getCourier().getId());
                    return new ResourceNotFoundException("not found courier");
                }));
        log.info("Update order by id " + id + ": " + orderResponse);
        return mapper
                .toDTO(orderRepository
                        .save(orderResponse));
    }

    public OrderCreateResponseDto orderCreate(OrderCreateRequestDto orderDto) {
        log.info("OrderCreateResponseDto input data: " + orderDto);
        Order order = orderCreateFromDto(orderDto);
        log.info("The order was saved: " + order);
        OrderCreateResponseDto orderCreateResponseDto = new OrderCreateResponseDto();
        orderCreateResponseDto.setId(order.getId());
        orderCreateResponseDto.setEstimatedTimeOfArrival(timeCalculator.calculator(order));
        orderCreateResponseDto.setSecretPaymentUrl(paymentService.pay(order));
        log.info("Response to the customer's request to create an order: " + orderCreateResponseDto);
        return orderCreateResponseDto;
    }

    private Order orderCreateFromDto(OrderCreateRequestDto orderDto) {
        Order order = Order
                .builder()
                .customer(customerRepository
                        .findById(orderDto.getCustomer())
                        .orElseThrow(() -> {
                            log.error("Not found Customer by id = " + orderDto.getCustomer());
                            return new ResourceNotFoundException("not found Customer");
                        }))
                .restaurant(restaurantRepository
                        .findById(orderDto.getRestaurant())
                        .orElseThrow(() -> {
                            log.error("Not found Restaurant by id = " + orderDto.getRestaurant());
                            return new ResourceNotFoundException("not found Restaurant");
                        }))
                .status(StatusOrders.CUSTOMER_CREATED)
                .timestamp(ZonedDateTime.now())
                .build();
        if (order.getRestaurant().getStatus().equals(StatusRestaurant.RESTAURANT_NOT_ACTIVE)) {
            throw new CreationException("The restaurant is closed");
        }
        order.setOrderItems(getOrderItemListFromDto(order, orderDto.getOrderItems()));
        log.info("Order created: " + order);
        orderRepository.save(order);
        orderItemRepository.saveAll(getOrderItemListFromDto(order, orderDto.getOrderItems()));
        log.info("The order is saved: " + order);
        return order;
    }

    private ArrayList<OrderItem> getOrderItemListFromDto(Order order, List<OrderItemCreateRequestDto> orderItemDtoList) {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        for (OrderItemCreateRequestDto itemDto : orderItemDtoList) {
            if (itemDto.getQuantity() < 1) {
                log.error("Quantity < 1");
                throw new CreationException("Quantity < 0");
            }
            if (itemDto.getRestaurantMenuItem() == null) {
                log.error("RestaurantMenuItem = null");
                throw new CreationException("RestaurantMenuItem = null");
            }
            RestaurantMenuItem restaurantMenuItem = restaurantMenuRepository
                    .findById(itemDto.getRestaurantMenuItem())
                    .orElseThrow(() -> {
                        log.error("Not found RestaurantMenuItem by id = " + itemDto.getRestaurantMenuItem());
                        return new ResourceNotFoundException("not found RestaurantMenuItem");
                    });
            if (restaurantMenuItem.getOrderItem() != null) {
                log.error("RestaurantMenuItem by id " + restaurantMenuItem.getId() + " is busy");
                throw new CreationException("RestaurantMenuItem is busy");
            }
            OrderItem orderItem = OrderItem
                    .builder()
                    .order(order)
                    .quantity(itemDto.getQuantity())
                    .restaurantMenuItem(restaurantMenuItem)
                    .build();
            orderItemArrayList.add(orderItem);
            log.info("OrderItem created: " + orderItem);
        }
        return orderItemArrayList;
    }

    public OrderDto updateOrderStatusById(UUID id, StatusOrders status) {
        log.info("UpdateOrderStatusById input data: status = " + status + ", id = " + id);
        Order order = getOrder(id);
        if (!checkNewStatus(order.getStatus(), status)) {
            throw new RequestException("The order status " + order.getStatus().toString()
                    + " cannot be changed to the specified");
        }
        log.info("Current order by id " + id + ": " + order);
        order.setStatus(status);
        order = orderRepository.save(order);
        log.info("New order by id " + id + ": " + order);
        OrderDto orderDto = mapper.toDTO(order);

        if (status.equals(StatusOrders.CUSTOMER_PAID)) {
            NotificationDto notificationDto = NotificationDto
                    .builder()
                    .message("Create new order № " + orderDto.getId() + " for restaurant by id " + orderDto.getRestaurant().getId())
                    .routingKey("restaurant.message")
                    .build();
            messageSender.sendMessage(notificationDto);
        }
        return orderDto;
    }

    private boolean checkNewStatus(StatusOrders orderStatus, StatusOrders newStatus) {
        log.info("checkNewStatus input data: orderStatus = " + orderStatus + ", newStatus = " + newStatus);
        if (newStatus.equals(StatusOrders.CUSTOMER_CANCELLED)
                || newStatus.equals(StatusOrders.KITCHEN_DENIED)
                || newStatus.equals(StatusOrders.DELIVERY_DENIED)) {
            return !orderStatus.equals(StatusOrders.CUSTOMER_CANCELLED)
                    && !orderStatus.equals(StatusOrders.KITCHEN_DENIED)
                    && !orderStatus.equals(StatusOrders.KITCHEN_REFUNDED)
                    && !orderStatus.equals(StatusOrders.DELIVERY_DENIED)
                    && !orderStatus.equals(StatusOrders.DELIVERY_REFUNDED);
        }
        return newStatus.equals(orderStatus.next());
    }

}
