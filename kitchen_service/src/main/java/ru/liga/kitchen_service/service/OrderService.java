package ru.liga.kitchen_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.model.Order;
import ru.liga.kitchen_service.repository.OrderRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    public KitchenResponseDto getDeliveriesByStatus(StatusOrders status) throws ResourceNotFoundException {
        if (status == null) {
            throw new ResourceNotFoundException();
        }
        List<Order> orderList = orderRepository.findAllByStatus(status);
        KitchenResponseDto kitchenResponseDto = new KitchenResponseDto();
        kitchenResponseDto.setOrders(orderList);
        kitchenResponseDto.setPageIndex(1);
        kitchenResponseDto.setPageCount(orderList.size());
        return kitchenResponseDto;
    }

    public Order getOrderById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return order;
    }

}
