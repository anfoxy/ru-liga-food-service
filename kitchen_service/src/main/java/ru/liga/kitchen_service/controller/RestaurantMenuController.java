package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.model.RestaurantMenuItem;
import ru.liga.kitchen_service.service.RestaurantMenuService;

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

    @Operation(summary = "добавить меню ресторана")
    @PostMapping("/create")
    public ResponseEntity<Object> createRestaurantMenu(@RequestBody RestaurantMenuItem restaurantMenuItem) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(restaurantMenuService.createRestaurantMenu(restaurantMenuItem));
    }

    @Operation(summary = "удалить меню ресторана по id")
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<String> deleteRestaurantMenu(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        restaurantMenuService.deleteRestaurantMenuById(id);
        return ResponseEntity
                .ok("RestaurantMenuItem with id:"+id+" deleted");
    }

    @Operation(summary = "добавить меню ресторана")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePriceRestaurantMenu(@PathVariable(value = "id") Long id, @RequestBody Double price) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(restaurantMenuService.updatePriceRestaurantMenu(id,price));
    }
}

