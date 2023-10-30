package ru.liga.kitchen_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.status.StatusOrders;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen/order")
public class OrderController {

    final private OrderService orderService;

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("/{restaurant_id}/status")
    public ResponseEntity<Object> getAllOrderByStatus(@PathVariable("restaurant_id") Long id,@RequestParam("status") StatusOrders status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.getAllOrderByStatus(status,id));
    }

    @Operation(summary = "Обновить статус заказа по ID")
    @PutMapping("/{id}/update/status")
    public ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") Long id, @RequestParam("status") StatusOrders status) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.updateOrderStatusById(id,status));
    }

}

