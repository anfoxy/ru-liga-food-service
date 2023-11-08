package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.commons.status.StatusRestaurant;

import java.util.UUID;

public interface RestaurantService {

    RestaurantDto getRestaurantById(UUID id);

    RestaurantDto updateRestaurantStatus(UUID id, StatusRestaurant restaurantActive);

}
