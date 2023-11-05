package ru.liga.kitchen_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.RequestException;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.exception.ServerException;
import ru.liga.kitchen_service.feign.OrderFeign;
import ru.liga.kitchen_service.mapper.OrderMapper;
import ru.liga.kitchen_service.model.Order;
import ru.liga.kitchen_service.repository.OrderRepository;
import ru.liga.kitchen_service.service.rabbit.MessageSenderCourier;
import ru.liga.kitchen_service.service.rabbit.MessageSenderCustomer;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeign orderFeign;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MessageSenderCourier senderCourierMassageMQ;
    private final MessageSenderCustomer senderCustomerMassageMQ;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    public KitchenResponseDto getAllOrderByStatus(StatusOrders status, Long id) {
        if (status == null) {
            throw new ResourceNotFoundException();
        }
        List<Order> orderList = orderRepository.findAllByStatusAndRestaurant_Id(status, id);
        KitchenResponseDto kitchenResponseDto = new KitchenResponseDto();
        kitchenResponseDto.setOrders(orderList);
        kitchenResponseDto.setPageIndex(1);
        kitchenResponseDto.setPageCount(orderList.size());
        return kitchenResponseDto;
    }

    public OrderDto getOrderById(Long id) {
        return orderMapper
                .toDTO(orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public OrderDto updateOrderStatusByIdAndSendMessage(Long id, StatusOrders statusOrders) {
        ResponseEntity<Object> responseEntity = orderFeign.updateOrderStatusById(id, String.valueOf(statusOrders));
        OrderDto order = getOrderFromResponseEntity(responseEntity);

        senderCustomerMassageMQ.sendOrder(order);
        return order;
    }

    public OrderDto deniedOrderById(Long id, StatusOrders statusOrders) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, statusOrders);
        paymentService.refund(order);
        return order;
    }

    public OrderDto completeOrderById(Long id, StatusOrders statusOrders) {
        OrderDto order = updateOrderStatusByIdAndSendMessage(id, statusOrders);
        senderCourierMassageMQ.sendOrder(order);
        return order;
    }

    public OrderDto pickingOrderById(Long idOrder, Long idCourier) {
        OrderDto order = getOrderById(idOrder);
        order.setCourier(new CourierDto().setId(idCourier));
        order.setStatus(StatusOrders.DELIVERY_PICKING);

        ResponseEntity<Object> orderResponseEntity = orderFeign.updateOrderById(idOrder, order);
        OrderDto newOrder = getOrderFromResponseEntity(orderResponseEntity);
        senderCustomerMassageMQ.sendOrder(newOrder);
        return newOrder;
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

}
