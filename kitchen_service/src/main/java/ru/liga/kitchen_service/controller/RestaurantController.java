package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.kitchen_service.exception.CreationException;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.model.Restaurant;
import ru.liga.kitchen_service.service.RestaurantService;
import ru.liga.commons.status.StatusOrders;

@Tag(name = "Api для работы с рестораном")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen/restaurant")
public class RestaurantController {

    final private RestaurantService restaurantService;

    @Operation(summary = "Получить рестораном по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "открыть ресторан с заданным ID")
    @GetMapping("/{id}/open")
    public ResponseEntity<Object> updateRestaurantStatusOpen(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantService.updateRestaurantStatus(id,StatusRestaurant.RESTAURANT_ACTIVE));
    }

    @Operation(summary = "закрыть ресторан с заданным ID")
    @GetMapping("/{id}/close")
    public ResponseEntity<Object> updateRestaurantStatusClose(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantService.updateRestaurantStatus(id,StatusRestaurant.RESTAURANT_NOT_ACTIVE));
    }

    @Operation(summary = "Добавить ресторан")
    @PostMapping("/create")
    public ResponseEntity<Object> createRestaurant(@RequestBody Restaurant restaurantMenu) throws CreationException {
        return ResponseEntity
                .ok(restaurantService.createRestaurant(restaurantMenu));
    }

    @Operation(summary = "Обновить данные ресторана по ID")
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateRestaurantById(@PathVariable("id") Long id, @RequestBody Restaurant restaurant) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(restaurantService.restaurantUpdate(id, restaurant));
    }

}

