package ru.liga.kitchen_service.service;

import ru.liga.commons.dto.dto_model.RestaurantMenuItemDto;

import java.util.List;

public interface RestaurantMenuService {

    RestaurantMenuItemDto getRestaurantMenuById(Long id);

    List<RestaurantMenuItemDto> getRestaurantMenuByRestaurantId(Long id);

    RestaurantMenuItemDto createRestaurantMenu(RestaurantMenuItemDto restaurantMenuItemDto);

    void deleteRestaurantMenuById(Long id);

    RestaurantMenuItemDto updatePriceRestaurantMenu(Long id, Double price);

}