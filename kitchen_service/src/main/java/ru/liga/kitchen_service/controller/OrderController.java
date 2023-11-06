package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.kitchen_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen-service/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Подтвердить курьера заказа")
    @PutMapping("/courier/confirm")
    public ResponseEntity<Object> confirmCourier(@RequestBody ConfirmCourierDto confirmCourierDto) {
        return ResponseEntity
                .ok(orderService.confirmCourier(confirmCourierDto));
    }

    @Operation(summary = "Завершить приготовление заказа на кухне с заданным ID")
    @PutMapping("/{id}/complete")
    public ResponseEntity<Object> completeOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.completeOrderById(id));
    }

    @Operation(summary = "принять заказ с заданным ID")
    @PutMapping("/{id}/accepted")
    public ResponseEntity<Object> acceptedOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.acceptedOrderById(id));
    }

    @Operation(summary = "Установить заказ с заданным ID на процесс приготовления")
    @PutMapping("/{id}/preparing")
    public ResponseEntity<Object> preparingOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.preparingOrderById(id));
    }

    @Operation(summary = "Отказаться от заказа с заданным ID")
    @PutMapping("/{id}/denied")
    public ResponseEntity<Object> deniedOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.deniedOrderById(id));
    }

}

