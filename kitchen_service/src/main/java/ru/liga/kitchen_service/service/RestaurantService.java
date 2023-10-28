package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.liga.commons.status.StatusRestaurant;
import ru.liga.kitchen_service.exception.CreationException;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.mapper.DtoMapper;
import ru.liga.kitchen_service.model.Restaurant;
import ru.liga.kitchen_service.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DtoMapper mapper;

    public Restaurant getRestaurantById(Long id) throws ResourceNotFoundException {
        return restaurantRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Restaurant createRestaurant(Restaurant restaurantRequest) throws CreationException {
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

        return restaurantRepository.save(restaurant);
    }

    public Restaurant restaurantUpdate(Long id, Restaurant restaurantRequest) throws ResourceNotFoundException {
        Restaurant restaurantResponse = getRestaurantById(id);
        mapper.updateRestaurantFromDto(restaurantRequest, restaurantResponse);
        restaurantResponse.setId(id);
        return restaurantRepository.save(restaurantResponse);
    }
    public Restaurant updateRestaurantStatus(Long id, StatusRestaurant restaurantActive) throws ResourceNotFoundException {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setStatus(restaurantActive);
        return restaurantRepository.save(restaurant);
    }
}
