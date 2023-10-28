package ru.liga.kitchen_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.feign.OrderFeign;
import ru.liga.kitchen_service.model.Order;
import ru.liga.kitchen_service.repository.OrderRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeign orderFeign;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MessageSender sendMassageMQ;

    public KitchenResponseDto getAllOrderByStatus(StatusOrders status,Long id) throws ResourceNotFoundException {
        if (status == null) {
            throw new ResourceNotFoundException();
        }
        List<Order> orderList = orderRepository.findAllByStatusAndRestaurant_Id(status,id);
        KitchenResponseDto kitchenResponseDto = new KitchenResponseDto();
        kitchenResponseDto.setOrders(orderList);
        kitchenResponseDto.setPageIndex(1);
        kitchenResponseDto.setPageCount(orderList.size());
        return kitchenResponseDto;
    }

    public Order getOrderById(Long id) throws ResourceNotFoundException {
        Order order = null;
        try {
            order = objectMapper.readValue(objectMapper.writeValueAsString(orderFeign.getOrderById(id).getBody()), Order.class);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException();
        }
        return order;
    }

    public Order updateOrderStatusById(Long id, StatusOrders statusOrders) throws  ResourceNotFoundException {

        Order order = null;
        try {
            order = objectMapper.readValue(objectMapper.writeValueAsString(orderFeign.updateOrderStatusById(id, String.valueOf(statusOrders)).getBody()), Order.class);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException();
        }
        if(statusOrders.equals(StatusOrders.DELIVERY_PENDING)){
            sendMassageMQ.sendOrder(tryToSerialyzeMessageAsString(order),order.getCustomer().getAddress());
        }
        return order;
    }

    private String tryToSerialyzeMessageAsString(Order messageModel) {
        String carInfoInLine = null;
        try {
            carInfoInLine = objectMapper.writeValueAsString(messageModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carInfoInLine;
    }

}
