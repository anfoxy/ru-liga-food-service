package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;

public interface OrderService {

    OrderDto deniedOrderById(Long idOrder);

    OrderDto completeOrderById(Long idOrder);

    OrderDto confirmCourier(ConfirmCourierDto confirmCourierDto);

    OrderDto acceptedOrderById(Long id);

    OrderDto preparingOrderById(Long id);

}
