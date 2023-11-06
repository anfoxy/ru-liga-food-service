package ru.liga.kitchen_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.exception.ServerException;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.feign.OrderFeign;
import ru.liga.kitchen_service.service.OrderService;
import ru.liga.kitchen_service.service.PaymentService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderFeign orderFeign;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MessageSenderCourier senderCourierMassageMQ;
    private final MessageSenderCustomer senderCustomerMassageMQ;
    private final MessageSenderRestaurant senderRestaurantMassageMQ;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @Override
    public OrderDto deniedOrderById(Long id) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_DENIED);
        paymentService.refund(order);
        return order;
    }

    @Override
    public OrderDto completeOrderById(Long id) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.DELIVERY_PENDING);
        senderCourierMassageMQ.sendOrder(order);
        return order;
    }

    @Override
    public OrderDto acceptedOrderById(Long id) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_ACCEPTED);
        return order;
    }

    @Override
    public OrderDto preparingOrderById(Long id) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_PREPARING);
        return order;
    }

    @Override
    public OrderDto confirmCourier(ConfirmCourierDto confirmCourierDto) {
        if (!confirmCourierDto.getStatus().equals("OK")) {
            senderRestaurantMassageMQ.sendMessage("There are no couriers available");
            return null;
        }

        OrderDto order = orderMapper.toDTO(orderRepository
                .findById(confirmCourierDto.getOrderID())
                .orElseThrow(ResourceNotFoundException::new));

        if (order.getCourier() != null) {
            throw new CreationException("The order was taken by another courier");
        }

        order.setCourier(new CourierDto().setId(confirmCourierDto.getCourierID()));
        order.setStatus(StatusOrders.DELIVERY_PICKING);

        ResponseEntity<Object> orderResponseEntity = orderFeign.updateOrderById(confirmCourierDto.getOrderID(), order);
        OrderDto newOrder = getOrderFromResponseEntity(orderResponseEntity);
        senderCustomerMassageMQ.sendOrder(newOrder);
        senderRestaurantMassageMQ.sendMessage("Found a courier with id " + confirmCourierDto.getCourierID()
                + " for order by id " + confirmCourierDto.getOrderID());
        return newOrder;
    }

    private OrderDto updateOrderStatusByIdAndSendMessage(Long id, StatusOrders statusOrders) {
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(statusOrders));
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        senderCustomerMassageMQ.sendOrder(order);
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
