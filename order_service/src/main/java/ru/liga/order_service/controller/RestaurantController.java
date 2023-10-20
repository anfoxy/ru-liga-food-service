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
import ru.liga.order_service.service.RestaurantService;

@Tag(name = "Api для работы с рестораном")
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final private RestaurantService restaurantService;

    @Operation(summary = "Получить рестораны")
    @GetMapping("/restaurants")
    public ResponseEntity<Object> getAllRestaurants() {
        return ResponseEntity
                .ok(restaurantService.getAllRestaurant());
    }

    @Operation(summary = "Получить рестораном по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "Получить ресторан по статусу")
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getRestaurantByStatus(@PathVariable("status") String status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(restaurantService.getRestaurantByStatus(status));
    }

}

