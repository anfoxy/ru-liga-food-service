package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.model.RestaurantMenuItem;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.repository.RestaurantMenuRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;

    public RestaurantMenuItem getRestaurantMenuById(Long id) throws ResourceNotFoundException {
        return restaurantMenuRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<RestaurantMenuItem> getRestaurantMenuByRestaurantId(Long id) {
        return restaurantMenuRepository.findAllByRestaurant_Id(id);
    }

}