package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.model.Restaurant;
import ru.liga.order_service.repository.RestaurantRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantById(Long id) throws ResourceNotFoundException {
        return restaurantRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Restaurant> getRestaurantByStatus(String status) {
        return restaurantRepository.findAllByStatus(status);
    }

    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }
}
