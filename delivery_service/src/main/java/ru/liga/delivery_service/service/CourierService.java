package ru.liga.delivery_service.service;

import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.status.StatusCourier;

import java.util.List;
import java.util.UUID;


public interface CourierService {

    CourierDto getCourierById(UUID id);

    List<CourierDto> getCourierByStatusActive();

    CourierDto getClosestCourier(String restaurantAddress, String district);

    void updateCourierStatusById(UUID id, StatusCourier statusCourier);

}
