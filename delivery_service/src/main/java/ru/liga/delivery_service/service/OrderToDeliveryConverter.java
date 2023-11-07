package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.CustomerForDeliveryDto;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.RestaurantForDeliveryDto;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.util.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderToDeliveryConverter {

    private final DeliveryCostCalculator deliveryCostCalculator;
    private final DistanceCalculator distanceCalculator;

    public DeliveryDto deliveryDtoCreateFromOrder(OrderDto orderDto, CourierDto courierDto) {
        return DeliveryDto
                .builder()
                .restaurant(RestaurantForDeliveryDto
                        .builder()
                        .address(orderDto.getRestaurant().getAddress())
                        .distance(distanceCalculator.calculator(courierDto.getCoordinates(), orderDto.getRestaurant().getAddress()))
                        .build())
                .customer(CustomerForDeliveryDto
                        .builder()
                        .address(orderDto.getCustomer().getAddress())
                        .distance(distanceCalculator.calculator(courierDto.getCoordinates(), orderDto.getCustomer().getAddress()))
                        .build())
                .payment(deliveryCostCalculator
                        .calculator(distanceCalculator
                                .calculator(orderDto.getRestaurant().getAddress(), orderDto.getCustomer().getAddress())))
                .orderId(orderDto.getId())
                .courierId(courierDto.getId())
                .orderAction(orderDto.getStatus())
                .build();
    }

    public List<DeliveryDto> deliveryDtoListCreateFromOrder(List<OrderDto> orderList) {
        List<DeliveryDto> deliveryDtoList = new ArrayList<>();
        for (OrderDto order : orderList) {
            deliveryDtoList.add(deliveryDtoCreateFromOrder(order, order.getCourier()));
        }
        return deliveryDtoList;
    }

}
