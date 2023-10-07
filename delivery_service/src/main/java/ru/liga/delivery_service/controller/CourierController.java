
package ru.liga.delivery_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.dto.DeliveryDto;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.service.DeliveryService;

@Tag(name = "Api для работы с доставкой")
@RequiredArgsConstructor
@RestController
public class CourierController {

    final private DeliveryService deliveryService;

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("/deliveries")
    public ResponseEntity<Object> getAllDelivery(@RequestParam("status") String status) {
        DeliveriesResponseDto deliveries = deliveryService.getDeliveriesByStatus(status);
        return ResponseEntity
                .ok(deliveries);
    }

    @Operation(summary = "Принять или завершить доставку")
    @PostMapping("/delivery/{id}")
    public ResponseEntity<Object> updateDeliveryActionById(@PathVariable("id") Long id, @RequestBody DeliveryDto request) throws ResourceNotFoundException {
        boolean actionResult = deliveryService.getDeliveriesByStatus(id, request.getOrderAction());
        if (actionResult) {
            return ResponseEntity
                    .ok("Action performed successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleException(ResourceNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

}

