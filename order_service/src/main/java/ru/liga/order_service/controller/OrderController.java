package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.service.OrderService;

import javax.validation.constraints.Min;

@Tag(name = "Api для работы с заказами",description = "В данном контроллере описаны методы для работы с заказами клиента")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить заказ по ID",
            description = "Получить заказ по заданному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") @Min(0) @Parameter(description = "Идентификатор заказа") Long id) {
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить заказы по id клиента",
            description = "Получить все заказы по заданному ID клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены")
    })
    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<Object> getAllOrderByCustomerId(@PathVariable("customer_id") @Min(0) @Parameter(description = "Идентификатор клиента") Long id) {
        return ResponseEntity
                .ok(orderService.getAllOrderByCustomerID(id));
    }

    @Operation(summary = "Получить заказы по статусу",
            description = "Получить все заказы по заданному статусу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены")
    })
    @GetMapping("/status")
    public ResponseEntity<Object> getAllOrderByStatus(@RequestParam("status") @Parameter(description = "статус заказа") StatusOrders status) {
        return ResponseEntity
                .ok(orderService.getAllOrderByStatus(status));
    }

    @Operation(summary = "Получить заказ по id ресторана",
            description = "Получить заказ по заданному ID ресторана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getOrderByRestaurantID(@PathVariable("restaurant_id") @Min(0) @Parameter(description = "Идентификатор ресторана") Long restaurant_id) {
        return ResponseEntity
                .ok(orderService.getOrderByRestaurantID(restaurant_id));
    }

    @Operation(summary = "Обновить данные заказа",
            description = "Обновить данные заказа по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateOrderById(@PathVariable("id") @Min(0) @Parameter(description = "Идентификатор заказа") Long id, @RequestBody @Parameter(description = "заказ") OrderDto order) {
        return ResponseEntity
                .ok(orderService.orderUpdate(id, order));
    }

    @Operation(summary = "Обновить статус заказа",
            description = "Обновить статус заказа по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PutMapping("/{id}/update/status")
    public ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") @Min(0) @Parameter(description = "Идентификатор заказа") Long id, @RequestParam("status") @Parameter(description = "статус") StatusOrders status) {
        return ResponseEntity
                .ok(orderService.updateOrderStatusById(id, status));
    }

    @Operation(summary = "Создать новый заказ",
            description = "Создать новый заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderCreateRequestDto order) {
        return ResponseEntity
                .ok(orderService.orderCreate(order));
    }

}

