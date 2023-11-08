package ru.liga.delivery_service.service;

import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusOrders;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;


public interface DeliveryService {

    OrderDto getDeliveryById(UUID id);

    List<DeliveryDto> getActiveDeliveries(UUID idCourier);

    OrderDto acceptedDelivery(UUID idOrder, UUID idCourier, HttpServletRequest request);

    OrderDto deniedDelivery(UUID id, HttpServletRequest request);

    OrderDto completeDelivery(UUID id, HttpServletRequest request);

    OrderDto deliveringDelivery(UUID id, HttpServletRequest request);

}
