package ru.liga.delivery_service.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.exception.CreationException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.model.Courier;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.delivery_service.service.DeliveryService;

@Tag(name = "Api для работы с доставкой")
@RequiredArgsConstructor
@RestController
public class DeliveryController {

    final private DeliveryService deliveryService;

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("/{id}/deliveries")
    public ResponseEntity<Object> getAllDelivery(@PathVariable("id") Long id,@RequestParam("status") StatusOrders status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(deliveryService.getDeliveriesByStatus(id,status));
    }

    @Operation(summary = "Принять или завершить доставку")
    @PostMapping("/{id}/update/delivery")
    public ResponseEntity<Object> updateDeliveryActionById(@PathVariable("id") Long id, @RequestBody DeliveryDto request) throws ResourceNotFoundException {
        boolean actionResult = deliveryService.updateDeliveryActionById(id, request.getOrderAction());
        if (actionResult) {
            return ResponseEntity
                    .ok("Action performed successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
    }

}

