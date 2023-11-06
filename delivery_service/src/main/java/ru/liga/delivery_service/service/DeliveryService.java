package ru.liga.delivery_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;


public interface DeliveryService {

    OrderDto getDeliveryById(Long id);

    DeliveriesResponseDto getDeliveriesByStatus(Long id, StatusOrders orderAction);

    OrderDto acceptedDelivery(Long idOrder, Long idCourier);

    OrderDto deniedDelivery(Long id);

    OrderDto completeDelivery(Long id);

    OrderDto deliveringDelivery(Long id);

}
