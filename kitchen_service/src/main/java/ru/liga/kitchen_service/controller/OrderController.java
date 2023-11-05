package ru.liga.kitchen_service.controller;

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
import ru.liga.kitchen_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kitchen-service/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить заказы ресторана по статусу")
    @GetMapping("/{restaurant_id}/status")
    public ResponseEntity<Object> getAllOrderByStatus(@PathVariable("restaurant_id") Long id,
                                                      @RequestParam("status") StatusOrders status) {
        return ResponseEntity
                .ok(orderService.getAllOrderByStatus(status, id));
    }

    @Operation(summary = "Подтвердить курьера заказа")
    @PutMapping("/{id}/courier/{id_courier}/picking")
    public ResponseEntity<Object> pickingOrderById(@PathVariable("id") Long id,
                                                        @PathVariable("id_courier") Long id_courier) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.pickingOrderById(id,id_courier));
    }

    @Operation(summary = "Завершить приготовление заказа на кухне с заданным ID")
    @PutMapping("/{id}/complete")
    public ResponseEntity<Object> completeOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.completeOrderById(id, StatusOrders.DELIVERY_PENDING));
    }

    @Operation(summary = "принять заказ с заданным ID")
    @PutMapping("/{id}/accepted")
    public ResponseEntity<Object> acceptedOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_ACCEPTED));
    }

    @Operation(summary = "Установить заказ с заданным ID на процесс приготовления")
    @PutMapping("/{id}/preparing")
    public ResponseEntity<Object> preparingOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.updateOrderStatusByIdAndSendMessage(id, StatusOrders.KITCHEN_PREPARING));
    }

    @Operation(summary = "Отказаться от заказа с заданным ID")
    @PutMapping("/{id}/denied")
    public ResponseEntity<Object> deniedOrderById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.deniedOrderById(id, StatusOrders.KITCHEN_DENIED));
    }

}

