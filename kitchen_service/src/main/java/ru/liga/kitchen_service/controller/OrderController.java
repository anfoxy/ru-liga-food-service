package ru.liga.kitchen_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.kitchen_service.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen-service/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Подтвердить курьера заказа")
    @PostMapping("/courier/confirm")
    public ResponseEntity<Object> confirmCourier(@RequestBody ConfirmCourierDto confirmCourierDto, HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.confirmCourier(confirmCourierDto, request));
    }

    @Operation(summary = "Завершить приготовление заказа на кухне с заданным ID")
    @PostMapping("/{id}/complete")
    public ResponseEntity<Object> completeOrderById(@PathVariable("id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.completeOrderById(id, request));
    }

    @Operation(summary = "Принять заказ с заданным ID")
    @PostMapping("/{id}/accepted")
    public ResponseEntity<Object> acceptedOrderById(@PathVariable("id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.acceptedOrderById(id, request));
    }

    @Operation(summary = "Отказаться от заказа с заданным ID")
    @PostMapping("/{id}/denied")
    public ResponseEntity<Object> deniedOrderById(@PathVariable("id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.deniedOrderById(id, request));
    }

}

