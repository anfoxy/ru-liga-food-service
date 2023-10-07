
package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
public class OrderController {

    final private OrderService orderService;

    @Operation(summary = "Получить заказы")
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders() {
        try {
            OrdersResponseDto orders = orderService.getAllOrders();
            return ResponseEntity
                    .ok(orders);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new OrdersResponseDto(null, 0, 0));
        }
    }

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity
                .ok(order);
    }

    @Operation(summary = "Обновить данные заказа по ID")
    @PutMapping("/order/{id}")
    public ResponseEntity<Object> updateOrderById(@PathVariable("id") Long id, @RequestBody OrderDto order) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.orderUpdate(id, order));
    }

    @Operation(summary = "Создать новый заказ")
    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderCreateRequestDto order) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.orderCreate(order.getRestaurantId(), order.getMenuItems()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleException(ResourceNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

}

