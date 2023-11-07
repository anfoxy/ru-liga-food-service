package ru.liga.delivery_service.service;

import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.status.StatusCourier;

import java.util.List;


public interface CourierService {

    CourierDto getCourierById(Long id);

    List<CourierDto> getCourierByStatusActive();

    CourierDto createCourier(CourierDto courierRequest);

    CourierDto getClosestCourier(String restaurantAddress, String district);

    CourierDto updateCourierById(Long id, CourierDto courierRequest);

    void updateCourierStatusById(Long id, StatusCourier statusCourier);

}
