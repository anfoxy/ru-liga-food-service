package ru.liga.delivery_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.NotificationDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.exception.ServerException;
import ru.liga.commons.mapper.OrderListMapper;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.feign.OrderFeign;
import ru.liga.delivery_service.feign.RestaurantFeign;
import ru.liga.delivery_service.rabbit.MessageSender;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.delivery_service.service.DeliveryService;
import ru.liga.delivery_service.service.OrderToDeliveryConverter;
import ru.liga.delivery_service.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final CourierService courierService;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final OrderToDeliveryConverter toDeliveryConverter;
    private final OrderMapper orderMapper;
    private final OrderListMapper orderListMapper;
    private final OrderFeign orderFeign;
    private final RestaurantFeign restaurantFeign;
    private final ObjectMapper objectMapper;
    private final MessageSender messageSender;

    public OrderDto getDeliveryById(UUID id) {
        return orderMapper
                .toDTO(orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public List<DeliveryDto> getActiveDeliveries(UUID idCourier) {
        List<Order> orderList = orderRepository.findAllByStatus(StatusOrders.DELIVERY_PENDING);
        CourierDto courierDto = courierService.getCourierById(idCourier);
        List<DeliveryDto> deliveryDto = toDeliveryConverter
                .deliveryDtoListCreateFromOrder(orderListMapper.toDTOList(orderList), courierDto);

        return deliveryDto;
    }

    public OrderDto acceptedDelivery(UUID idOrder, UUID idCourier, HttpServletRequest request) {
        log.info("AcceptedDelivery input data: idOrder = " + idOrder + ", idCourier = " + idCourier);
        ConfirmCourierDto confirmCourierDto = ConfirmCourierDto
                .builder()
                .orderID(idOrder)
                .courierID(idCourier)
                .build();
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> restaurantResponseEntity = restaurantFeign.confirmCourier(confirmCourierDto, authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(restaurantResponseEntity);
        log.info("AcceptedDelivery restaurantResponseEntity = " + order);
        courierService.updateCourierStatusById(order.getCourier().getId(), StatusCourier.COURIER_DELIVERS);
        log.info("The status of the courier has been updated to " + StatusCourier.COURIER_DELIVERS);

        return order;
    }

    public OrderDto deniedDelivery(UUID id, HttpServletRequest request) {
        log.info("DeniedDelivery input data: id = " + id );
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.DELIVERY_DENIED, request);
        log.info("Order changed to " + order);
        paymentService.refund(order);
        courierService.updateCourierStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        log.info("The status of the courier has been updated to " + StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto completeDelivery(UUID id, HttpServletRequest request) {
        log.info("CompleteDelivery input data: id = " + id );
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.DELIVERY_COMPLETE, request);
        log.info("Order changed to " + order);
        courierService.updateCourierStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        log.info("The status of the courier has been updated to " + StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto deliveringDelivery(UUID id, HttpServletRequest request) {
        log.info("DeliveringDelivery input data: id = " + id );
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, StatusOrders.DELIVERY_DELIVERING, request);
        log.info("Order changed to " + order);
        return order;
    }

    private OrderDto updateOrderStatusByIdAndSendMessage(UUID id, StatusOrders statusOrders, HttpServletRequest request) {
        log.info("UpdateOrderStatusByIdAndSendMessage input data: id = " + id + ", statusOrders = " + statusOrders);
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(statusOrders), authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        log.info("Order changed to " + order);
        messageSender.sendMessage(NotificationDto
                .builder()
                .message("Order № " + order.getId() + " status changed to " + order.getStatus().toString())
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
