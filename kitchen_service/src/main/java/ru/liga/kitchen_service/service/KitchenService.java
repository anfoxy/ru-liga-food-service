package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.repository.OrderRepository;
import ru.liga.kitchen_service.model.Order;
import ru.liga.commons.status.StatusOrders;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {

    final private OrderRepository orderRepository;
    public KitchenResponseDto getDeliveriesByStatus(StatusOrders status) throws ResourceNotFoundException {
        if (status == null) {
            throw new ResourceNotFoundException();
        }
        List<Order> orderList = orderRepository.findAllByStatus(status);
        KitchenResponseDto kitchenResponseDto = new KitchenResponseDto();
        kitchenResponseDto.setOrders(orderList);
        kitchenResponseDto.setPageIndex(orderList.size());
        kitchenResponseDto.setPageCount(1);
        return kitchenResponseDto;
    }

}
