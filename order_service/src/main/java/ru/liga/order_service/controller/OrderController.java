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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.service.OrderService;

@Tag(name = "Api для работы с заказами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить заказы по id клиента")
    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<Object> getAllOrderByCustomerId(@PathVariable("customer_id") Long id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.getAllOrderByCustomer(id));
    }

    //Возможно убрать
    @Operation(summary = "Получить заказы по статусу")
    @GetMapping("/status")
    public ResponseEntity<Object> getAllOrderByStatus(@RequestParam("status") StatusOrders status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.getAllOrderByStatus(status));
    }

    @Operation(summary = "Получить заказ по id ресторана")
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getOrderByRestaurantID(@PathVariable("restaurant_id") Long restaurant_id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.getOrderByRestaurantID(restaurant_id));
    }

    @Operation(summary = "Обновить данные заказа по ID")
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateOrderById(@PathVariable("id") Long id, @RequestBody Order order) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.orderUpdate(id, order));
    }

    @Operation(summary = "Обновить статус заказа по ID")
    @PutMapping("/{id}/update/status")
    public ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") Long id, @RequestParam("status") String status) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderService.updateOrderStatusById(id, status));
    }

    @Operation(summary = "Создать новый заказ")
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderCreateRequestDto order) throws ResourceNotFoundException, CreationException {
        return ResponseEntity
                .ok(orderService.orderCreate(order));
    }

}

