package ru.liga.order_service.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
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
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить заказ по id ресторана")
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getOrderByRestaurantID(@PathVariable("restaurant_id") Long restaurant_id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.getOrderByRestaurantID(restaurant_id));
    }

    @Operation(summary = "Обновить данные заказа по ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrderById(@PathVariable("id") Long id, @RequestBody Order order) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.orderUpdate(id, order));
    }

    @Operation(summary = "Создать новый заказ")
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderCreateRequestDto order) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.orderCreate(order));
    }

}

