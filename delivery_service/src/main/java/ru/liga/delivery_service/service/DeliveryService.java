package ru.liga.delivery_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.CustomerForDeliveryDTO;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.RestaurantForDeliveryDTO;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.exception.RequestException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.exception.ServerException;
import ru.liga.delivery_service.feign.OrderFeign;
import ru.liga.delivery_service.feign.RestaurantFeign;
import ru.liga.delivery_service.mapper.OrderMapper;
import ru.liga.delivery_service.model.Order;
import ru.liga.delivery_service.repository.OrderRepository;
import ru.liga.delivery_service.service.rabbit.MessageSenderCustomer;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final CourierService courierService;
    private final OrderRepository orderRepository;
    private final DeliveryCostCalculator deliveryCostCalculator;
    private final DistanceCalculator distanceCalculator;
    private final OrderMapper orderMapper;
    private final OrderFeign orderFeign;
    private final RestaurantFeign restaurantFeign;
    private final ObjectMapper objectMapper;
    private final MessageSenderCustomer messageSender;

    public OrderDto getOrderById(Long id) {
        return orderMapper
                .toDTO(orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public DeliveriesResponseDto getDeliveriesByStatus(Long id, StatusOrders orderAction) {
        if (id < 0) {
            throw new RequestException();
        }
        List<Order> orderList = orderRepository.findAllByStatusAndCourier_Id(orderAction, id);

        DeliveriesResponseDto deliveriesResponseDto = new DeliveriesResponseDto();
        deliveriesResponseDto.setDelivery(deliveryDtoListCreateFromOrder(orderList));
        deliveriesResponseDto.setPageCount(orderList.size());
        deliveriesResponseDto.setPageIndex(0);
        return deliveriesResponseDto;
    }

    private List<DeliveryDto> deliveryDtoListCreateFromOrder(List<Order> orderList) {
        List<DeliveryDto> deliveryDtoList = new ArrayList<>();
        for (Order order : orderList) {
            DeliveryDto delivery = DeliveryDto
                    .builder()
                    .restaurant(RestaurantForDeliveryDTO
                            .builder()
                            .address(order.getRestaurant().getAddress())
                            .distance(distanceCalculator.calculator(order.getCourier().getCoordinates(), order.getRestaurant().getAddress()))
                            .build())
                    .customer(CustomerForDeliveryDTO
                            .builder()
                            .address(order.getCustomer().getAddress())
                            .distance(distanceCalculator.calculator(order.getCourier().getCoordinates(), order.getCustomer().getAddress()))
                            .build())
                    .payment(deliveryCostCalculator.calculator(
                            distanceCalculator.calculator(
                                    order.getRestaurant().getAddress(),
                                    order.getCustomer().getAddress())))
                    .orderId(order.getId())
                    .courierId(order.getCourier().getId())
                    .orderAction(order.getStatus())
                    .build();
            deliveryDtoList.add(delivery);
        }
        return deliveryDtoList;
    }

    private OrderDto getOrderFromResponseEntity(ResponseEntity<Object> responseEntity) {
        OrderDto order = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            try {
                order = objectMapper.readValue(objectMapper.writeValueAsString(responseEntity.getBody()), OrderDto.class);
            } catch (JsonProcessingException e) {
                throw new ServerException();
            }
        } else {
            if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ResourceNotFoundException();
            } else if (responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new RequestException();
            } else {
                throw new ServerException();
            }
        }
        return order;
    }

    public OrderDto acceptedDelivery(Long idOrder, Long idCourier) {
        OrderDto order = getOrderById(idOrder);
        order.setCourier(courierService.getCourierById(idCourier));
        order.setStatus(StatusOrders.DELIVERY_PICKING);

        ResponseEntity<Object> orderResponseEntity = orderFeign.updateOrderById(idOrder, order);
        getOrderFromResponseEntity(orderResponseEntity);

        CourierDto courier = courierService.getCourierById(idCourier);
        courier.setStatus(StatusCourier.COURIER_DELIVERS);
        courierService.courierUpdate(idCourier, courier);

        ResponseEntity<Object> restaurantResponseEntity = restaurantFeign.updateOrderStatusById(idOrder, StatusOrders.DELIVERY_PICKING);
        return getOrderFromResponseEntity(restaurantResponseEntity);
    }

    public OrderDto deniedDelivery(Long id) {
        ResponseEntity<Object> restaurantResponseEntity = restaurantFeign.updateOrderStatusById(id, StatusOrders.DELIVERY_DENIED);
        return getOrderFromResponseEntity(restaurantResponseEntity);
    }

    public OrderDto updateDeliveryActionById(Long id, StatusOrders status) {
        if (!(status.equals(StatusOrders.DELIVERY_DELIVERING)
                || status.equals(StatusOrders.DELIVERY_COMPLETE))) {
            throw new RequestException("invalid status");
        }
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(status));
        OrderDto order = getOrderFromResponseEntity(responseEntity);
        messageSender.sendOrder(order);
        return order;
    }

}
