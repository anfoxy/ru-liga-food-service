package ru.liga.delivery_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.RequestException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.exception.ServerException;
import ru.liga.commons.mapper.OrderListMapper;
import ru.liga.commons.mapper.OrderMapper;
import ru.liga.commons.model.Order;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.feign.OrderFeign;
import ru.liga.delivery_service.feign.RestaurantFeign;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.delivery_service.service.DeliveryService;
import ru.liga.delivery_service.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
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
    private final MessageSenderCustomer messageSender;

    public OrderDto getDeliveryById(Long id) {
        return orderMapper
                .toDTO(orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public DeliveriesResponseDto getDeliveriesByStatus(Long id, StatusOrders orderAction) {
        if (id < 0) {
            throw new RequestException();
        }
        List<Order> orderList = orderRepository.findAllByStatusAndCourier_Id(orderAction, id);

        DeliveriesResponseDto deliveriesResponseDto = new DeliveriesResponseDto();
        deliveriesResponseDto.setDelivery(toDeliveryConverter
                .deliveryDtoListCreateFromOrder(orderListMapper.toDTOList(orderList)));
        deliveriesResponseDto.setPageCount(orderList.size());
        deliveriesResponseDto.setPageIndex(0);
        return deliveriesResponseDto;
    }

    public OrderDto acceptedDelivery(Long idOrder, Long idCourier, HttpServletRequest request) {
        ConfirmCourierDto confirmCourierDto =  ConfirmCourierDto
                .builder()
                .orderID(idOrder)
                .courierID(idCourier)
                .build();
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> restaurantResponseEntity = restaurantFeign.pickingOrderById(confirmCourierDto, authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(restaurantResponseEntity);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_DELIVERS);
        return order;
    }

    public OrderDto deniedDelivery(Long id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(StatusOrders.DELIVERY_DENIED), authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        messageSender.sendOrder(order);
        paymentService.refund(order);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto completeDelivery(Long id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(StatusOrders.DELIVERY_COMPLETE), authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        messageSender.sendOrder(order);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto deliveringDelivery(Long id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(StatusOrders.DELIVERY_DELIVERING), authorizationHeader);
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        messageSender.sendOrder(order);
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
