package ru.liga.kitchen_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.RestaurantMapper;
import ru.liga.commons.model.Restaurant;
import ru.liga.commons.repositories.RestaurantRepository;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.kitchen_service.service.RestaurantService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantDto getRestaurantById(UUID id) {
        return mapper
                .toDTO(restaurantRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new));
    }

    public RestaurantDto updateRestaurantStatus(UUID id, StatusRestaurant restaurantActive) {
        log.info("UpdateOrderStatusByIdAndSendMessage input data: id = " + id + ", restaurantActive = " + restaurantActive);
        Restaurant restaurant = mapper.toModel(getRestaurantById(id));
        restaurant.setStatus(restaurantActive);
        log.info("Restaurant changed to " + restaurant);
        return mapper
                .toDTO(restaurantRepository.save(restaurant));
    }

}
