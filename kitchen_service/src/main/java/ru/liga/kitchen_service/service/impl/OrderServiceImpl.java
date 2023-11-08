package ru.liga.kitchen_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.exception.ServerException;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.feign.OrderFeign;
import ru.liga.kitchen_service.rabbit.MessageSender;
import ru.liga.kitchen_service.service.OrderService;
import ru.liga.kitchen_service.service.PaymentService;
import ru.liga.kitchen_service.rabbit.MessageSenderCourier;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderFeign orderFeign;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MessageSenderCourier senderCourierMassageMQ;
    private final MessageSender messageSender;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @Override
    public OrderDto deniedOrderById(UUID id, HttpServletRequest request) {
        log.info("DeniedOrderById input data: id = " + id);
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_DENIED, request);
        log.info("Order changed to " + order);
        paymentService.refund(order);
        return order;
    }

    @Override
    public OrderDto completeOrderById(UUID id, HttpServletRequest request) {
        log.info("CompleteOrderById input data: id = " + id);
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.DELIVERY_PENDING, request);
        log.info("Order changed to " + order);
        senderCourierMassageMQ.sendOrder(order);
        return order;
    }

    @Override
    public OrderDto acceptedOrderById(UUID id, HttpServletRequest request) {
        log.info("AcceptedOrderById input data: id = " + id);
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_ACCEPTED, request);
        log.info("Order changed to " + order);
        OrderDto newOrder =  updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_PREPARING, request);
        log.info("Order changed to " + order);
        return newOrder;
    }

    @Override
    public OrderDto confirmCourier(ConfirmCourierDto confirmCourierDto, HttpServletRequest request) {
        log.info("ConfirmCourier input data: confirmCourierDto = " + confirmCourierDto);
        OrderDto order = orderMapper.toDTO(orderRepository
                .findById(confirmCourierDto.getOrderID())
                .orElseThrow(ResourceNotFoundException::new));
        log.info("Order by id = " +confirmCourierDto.getOrderID() + " = " + order);
        if (order.getCourier() != null) {
            throw new CreationException("The order was taken by another courier");
        }
        order.setCourier(new CourierDto().setId(confirmCourierDto.getCourierID()));
        order.setStatus(StatusOrders.DELIVERY_PICKING);
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> orderResponseEntity = orderFeign
                .orderUpdateCourierAndStatus(confirmCourierDto.getOrderID(), order, authorizationHeader);
        OrderDto newOrder = getOrderFromResponseEntity(orderResponseEntity);
        log.info("Order changed to " + newOrder);
        messageSender.sendMessage(NotificationDto
                .builder()
                .message("Order № " + newOrder.getId() + " status changed to " + newOrder.getStatus().toString())
                .routingKey("customer.message")
                .build());
        messageSender.sendMessage(NotificationDto
                .builder()
                .message("Courier № " + confirmCourierDto.getCourierID() + " found for order № " + confirmCourierDto.getOrderID())
                .routingKey("restaurant.message")
                .build());
        return newOrder;
    }

    private OrderDto updateOrderStatusByIdAndSendMessage(UUID id, StatusOrders statusOrders, HttpServletRequest request) {
        log.info("UpdateOrderStatusByIdAndSendMessage input data: id = " + id + ", statusOrders = " + statusOrders);
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(statusOrders), authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        log.info("Order changed to " + order);
        messageSender.sendMessage(NotificationDto
                .builder()
                .message("Order № " + id + " status changed to " + statusOrders.toString())
                .routingKey("customer.message")
                .build());
        return order;
    }

    private OrderDto getOrderFromResponseEntity(ResponseEntity<Object> responseEntity) {
        OrderDto order = null;
        try {
            order = objectMapper.readValue(objectMapper.writeValueAsString(responseEntity.getBody()), OrderDto.class);
        } catch (JsonProcessingException e) {
            throw new ServerException();
        }
        return order;
    }

}
