
package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.service.KitchenService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    final private KitchenService kitchenService;

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllDelivery(@RequestParam("status") String status) throws ResourceNotFoundException {
        KitchenResponseDto deliveries = kitchenService.getDeliveriesByStatus(status);
        return ResponseEntity
                .ok(deliveries);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleException(ResourceNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

}

