package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setId(null);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant restaurantUpdate(Long id, Restaurant restaurantRequest) throws ResourceNotFoundException {
        Restaurant restaurantResponse = getRestaurantById(id);
        mapper.updateRestaurantFromDto(restaurantRequest, restaurantResponse);
        restaurantResponse.setId(id);
        restaurantRepository.save(restaurantResponse);
        return restaurantResponse;
    }
}
