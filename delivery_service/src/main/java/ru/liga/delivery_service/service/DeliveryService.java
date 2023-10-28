package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.CustomerDTO;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.RestaurantDTO;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.model.Courier;
import ru.liga.delivery_service.model.Order;
import ru.liga.delivery_service.repository.CourierRepository;
import ru.liga.delivery_service.repository.OrderRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;

    public DeliveriesResponseDto getDeliveriesByStatus(Long id, StatusOrders orderAction) throws ResourceNotFoundException {
        if (id < 0) {
            throw new ResourceNotFoundException();
        }
        List<Order> orderList = orderRepository.findAllByStatusAndCourier_Id(orderAction,id);

        DeliveriesResponseDto deliveriesResponseDto = new DeliveriesResponseDto();
        deliveriesResponseDto.setDelivery(DeliveryDtoCreateFromOrder(orderList));
        deliveriesResponseDto.setPageCount(orderList.size());
        deliveriesResponseDto.setPageIndex(0);
        return deliveriesResponseDto;

    }
    private List<DeliveryDto> DeliveryDtoCreateFromOrder(List<Order> orderList) {
        List<DeliveryDto> deliveryDtoList = new ArrayList<>();
        for (Order order: orderList) {
            DeliveryDto deliveryDto = DeliveryDto
                    .builder()
                    .orderId(order.getId())
                    .restaurant(RestaurantDTO
                            .builder()
                            .distance(100.0)
                            .address(order.getRestaurant().getAddress())
                            .build())
                    .customer(CustomerDTO
                            .builder()
                            .distance(100.0)
                            .address(order.getCustomer().getAddress())
                            .build())
                    .payment(500.0)
                    .orderAction(order.getStatus())
                    .build();
            deliveryDtoList.add(deliveryDto);
        }
        return deliveryDtoList;
    }

    public boolean updateDeliveryActionById(Long id, StatusOrders orderAction) {
        return true;
    }
}
