package ru.liga.delivery_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.exception.RequestException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.exception.ServerException;
import ru.liga.delivery_service.feign.OrderFeign;
import ru.liga.delivery_service.feign.RestaurantFeign;
import ru.liga.delivery_service.mapper.OrderListMapper;
import ru.liga.delivery_service.mapper.OrderMapper;
import ru.liga.delivery_service.model.Order;
import ru.liga.delivery_service.repository.OrderRepository;
import ru.liga.delivery_service.service.rabbit.MessageSenderCustomer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

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

    private OrderDto getOrderFromResponseEntity(ResponseEntity<Object> responseEntity) {
        OrderDto order = null;
        try {
            order = objectMapper.readValue(objectMapper.writeValueAsString(responseEntity.getBody()), OrderDto.class);
        } catch (JsonProcessingException e) {
            throw new ServerException();
        }
        return order;
    }

    public OrderDto acceptedDelivery(Long idOrder, Long idCourier) {
        ResponseEntity<Object> restaurantResponseEntity = restaurantFeign.pickingOrderById(idOrder, idCourier);
        OrderDto order = getOrderFromResponseEntity(restaurantResponseEntity);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_DELIVERS);
        return order;
    }

    public OrderDto deniedDelivery(Long id,StatusOrders statusOrders) {
        OrderDto order = updateDeliveryActionById(id, statusOrders);
        paymentService.refund(order);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto completeDelivery(Long id,StatusOrders statusOrders) {
        OrderDto order = updateDeliveryActionById(id, statusOrders);
        courierService.courierUpdateStatusById(order.getCourier().getId(), StatusCourier.COURIER_ACTIVE);
        return order;
    }

    public OrderDto updateDeliveryActionById(Long id, StatusOrders status) {
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(status));
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        messageSender.sendOrder(order);
        return order;
    }

}
