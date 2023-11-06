package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.commons.status.StatusRestaurant;

public interface RestaurantService {

    RestaurantDto getRestaurantById(Long id);

    RestaurantDto createRestaurant(RestaurantDto restaurantRequest);

    RestaurantDto restaurantUpdate(Long id, RestaurantDto restaurantRequest);

    RestaurantDto updateRestaurantStatus(Long id, StatusRestaurant restaurantActive);

}
