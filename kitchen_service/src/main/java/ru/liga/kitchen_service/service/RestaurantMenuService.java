package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.RestaurantMenuItemDto;
import ru.liga.kitchen_service.exception.CreationException;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.mapper.RestaurantMenuListMapper;
import ru.liga.kitchen_service.mapper.RestaurantMenuMapper;
import ru.liga.kitchen_service.repository.OrderItemRepository;
import ru.liga.kitchen_service.repository.RestaurantMenuRepository;
import ru.liga.kitchen_service.repository.RestaurantRepository;
import ru.liga.kitchen_service.model.OrderItem;
import ru.liga.kitchen_service.model.RestaurantMenuItem;
import ru.liga.commons.status.StatusRestaurantMenu;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuMapper mapper;
    private final RestaurantMenuListMapper listMapper;

    public RestaurantMenuItemDto getRestaurantMenuById(Long id) {
        return mapper
                .toDTO(restaurantMenuRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public List<RestaurantMenuItemDto> getRestaurantMenuByRestaurantId(Long id) {
        return listMapper
                .toDTOList(restaurantMenuRepository.findAllByRestaurant_Id(id));
    }

    public RestaurantMenuItemDto createRestaurantMenu(RestaurantMenuItemDto restaurantMenuItemDto) {
        if (restaurantMenuItemDto.getRestaurant() == null) {
            throw new CreationException("Bad request");
        }

        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setRestaurant(restaurantRepository
                .findById(restaurantMenuItemDto.getRestaurant().getId())
                .orElseThrow(() -> new ResourceNotFoundException("not found Restaurant")));
        restaurantMenuItem.setId(null);

        return mapper
                .toDTO(restaurantMenuRepository.save(restaurantMenuItem));
    }

    public void deleteRestaurantMenuById(Long id) {
        RestaurantMenuItem restaurantMenuItem = mapper.toModel(getRestaurantMenuById(id));
        restaurantMenuItem.setStatus(StatusRestaurantMenu.RESTAURANT_MENU_DENIED);
        restaurantMenuRepository.save(restaurantMenuItem);
    }

    public RestaurantMenuItemDto updatePriceRestaurantMenu(Long id, Double price) {
        RestaurantMenuItem restaurantMenuItem = mapper.toModel(getRestaurantMenuById(id));
        restaurantMenuItem.setPrice(price);
        restaurantMenuRepository.save(restaurantMenuItem);

        OrderItem newOrderItemPrice = restaurantMenuItem.getOrderItem();
        if (newOrderItemPrice != null) {
            newOrderItemPrice.setPrice(newOrderItemPrice.getQuantity() * price);
            orderItemRepository.save(newOrderItemPrice);
        }

        return mapper
                .toDTO(restaurantMenuItem);
    }

}