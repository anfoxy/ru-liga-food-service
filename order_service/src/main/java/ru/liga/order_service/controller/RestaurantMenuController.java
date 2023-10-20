package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.service.RestaurantMenuService;

@Tag(name = "Api для работы с меню ресторана")
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant_menu")
public class RestaurantMenuController {

    final private RestaurantMenuService restaurantMenuService;

    @Operation(summary = "Получить меню ресторана по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantMenuById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantMenuService.getRestaurantMenuById(id));
    }

    @Operation(summary = "Получить меню ресторана по id ресторана")
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getRestaurantMenuByRestaurantId(@PathVariable("restaurant_id") Long id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(restaurantMenuService.getRestaurantMenuByRestaurantId(id));
    }

}

