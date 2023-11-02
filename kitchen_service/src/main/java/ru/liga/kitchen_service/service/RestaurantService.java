package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.kitchen_service.exception.CreationException;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.mapper.RestaurantMapper;
import ru.liga.kitchen_service.model.Restaurant;
import ru.liga.kitchen_service.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantDto getRestaurantById(Long id) {
        return mapper
                .toDTO(restaurantRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new));
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantRequest) {
        if (restaurantRequest.getAddress() == null
                || restaurantRequest.getStatus() == null
                || restaurantRequest.getName() == null) {
            throw new CreationException("Bad request");
        }

        Restaurant restaurant = Restaurant
                .builder()
                .status(restaurantRequest.getStatus())
                .name(restaurantRequest.getName())
                .address(restaurantRequest.getAddress())
                .build();

        return mapper
                .toDTO(restaurantRepository.save(restaurant));
    }

    public RestaurantDto restaurantUpdate(Long id, RestaurantDto restaurantRequest) {
        Restaurant restaurantResponse = mapper.toModel(getRestaurantById(id));
        mapper.updateRestaurantFromDto(restaurantRequest, restaurantResponse);
        restaurantResponse.setId(id);
        return mapper
                .toDTO(restaurantRepository.save(restaurantResponse));
    }

    public RestaurantDto updateRestaurantStatus(Long id, StatusRestaurant restaurantActive) {
        Restaurant restaurant = mapper.toModel(getRestaurantById(id));
        restaurant.setStatus(restaurantActive);
        return mapper
                .toDTO(restaurantRepository.save(restaurant));
    }

}
