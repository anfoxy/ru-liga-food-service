package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.model.OrderItem;
import ru.liga.kitchen_service.model.RestaurantMenuItem;
import ru.liga.kitchen_service.repository.OrderItemRepository;
import ru.liga.kitchen_service.repository.RestaurantMenuRepository;
import ru.liga.kitchen_service.repository.RestaurantRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderItemRepository orderItemRepository;

    public RestaurantMenuItem getRestaurantMenuById(Long id) throws ResourceNotFoundException {
        return restaurantMenuRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<RestaurantMenuItem> getRestaurantMenuByRestaurantId(Long id) {
        return restaurantMenuRepository.findAllByRestaurant_Id(id);
    }

    public RestaurantMenuItem createRestaurantMenu(RestaurantMenuItem restaurantMenuItem) throws ResourceNotFoundException {
        restaurantMenuItem.setRestaurant(restaurantRepository
                .findById(restaurantMenuItem.getRestaurant().getId())
                .orElseThrow(ResourceNotFoundException::new));
        restaurantMenuItem.setId(null);
        return restaurantMenuRepository.save(restaurantMenuItem);
    }

    public void deleteRestaurantMenuById(Long id) throws ResourceNotFoundException {
        RestaurantMenuItem restaurantMenuItem = restaurantMenuRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        restaurantMenuItem.setStatus("Deleted");
        restaurantMenuRepository.save(restaurantMenuItem);
    }

    public RestaurantMenuItem updatePriceRestaurantMenu(Long id, Double price) throws ResourceNotFoundException {

        RestaurantMenuItem restaurantMenuItem = restaurantMenuRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        restaurantMenuItem.setPrice(price);
        restaurantMenuRepository.save(restaurantMenuItem);

        OrderItem newOrderItemPrice = restaurantMenuItem.getOrderItem();
        newOrderItemPrice.setPrice(newOrderItemPrice.getQuantity()*price);
        orderItemRepository.save(newOrderItemPrice);
        return restaurantMenuItem;
    }
}