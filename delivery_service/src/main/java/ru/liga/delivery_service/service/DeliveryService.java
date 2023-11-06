package ru.liga.delivery_service.service;

import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;

import javax.servlet.http.HttpServletRequest;


public interface DeliveryService {

    OrderDto getDeliveryById(Long id);

    DeliveriesResponseDto getDeliveriesByStatus(Long id, StatusOrders orderAction);

    OrderDto acceptedDelivery(Long idOrder, Long idCourier, HttpServletRequest request);

    OrderDto deniedDelivery(Long id, HttpServletRequest request);

    OrderDto completeDelivery(Long id, HttpServletRequest request);

    OrderDto deliveringDelivery(Long id, HttpServletRequest request);

}
