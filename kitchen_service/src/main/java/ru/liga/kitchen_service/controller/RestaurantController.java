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
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.kitchen_service.service.RestaurantService;

import java.util.UUID;

@Tag(name = "Api для работы с рестораном")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen-service/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Открыть ресторан с заданным ID")
    @PostMapping("/{id}/open")
    public ResponseEntity<Object> updateRestaurantStatusOpen(@PathVariable("id") UUID id) {
        return ResponseEntity
                .ok(restaurantService.updateRestaurantStatus(id, StatusRestaurant.RESTAURANT_ACTIVE));
    }

    @Operation(summary = "Закрыть ресторан с заданным ID")
    @PostMapping("/{id}/close")
    public ResponseEntity<Object> updateRestaurantStatusClose(@PathVariable("id") UUID id) {
        return ResponseEntity
                .ok(restaurantService.updateRestaurantStatus(id, StatusRestaurant.RESTAURANT_NOT_ACTIVE));
    }

}

