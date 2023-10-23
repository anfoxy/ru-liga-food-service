package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.service.KitchenService;
import ru.liga.commons.status.StatusOrders;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    final private KitchenService kitchenService;

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllDelivery(@RequestParam("status") StatusOrders status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(kitchenService.getDeliveriesByStatus(status));
    }

}

