package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    OrderDto deniedOrderById(Long idOrder, HttpServletRequest request);

    OrderDto completeOrderById(Long idOrder, HttpServletRequest request);

    OrderDto confirmCourier(ConfirmCourierDto confirmCourierDto, HttpServletRequest request);

    OrderDto acceptedOrderById(Long id, HttpServletRequest request);

    OrderDto preparingOrderById(Long id, HttpServletRequest request);

}
