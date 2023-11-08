package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface OrderService {

    OrderDto deniedOrderById(UUID idOrder, HttpServletRequest request);

    OrderDto completeOrderById(UUID idOrder, HttpServletRequest request);

    OrderDto confirmCourier(ConfirmCourierDto confirmCourierDto, HttpServletRequest request);

    OrderDto acceptedOrderById(UUID id, HttpServletRequest request);

}
