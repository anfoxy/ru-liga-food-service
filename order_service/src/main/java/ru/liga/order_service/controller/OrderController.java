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
import java.util.UUID;

@Tag(name = "Api для работы с заказами", description = "В данном контроллере описаны методы для работы с заказами клиента")
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
    public ResponseEntity<Object> getOrderById(@PathVariable("id") @Parameter(description = "Идентификатор заказа") UUID id) {
        return ResponseEntity
                .ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Получить заказы по id клиента",
            description = "Получить все заказы по заданному ID клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены")
    })
    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<Object> getAllOrderByCustomerId(@PathVariable("customer_id") @Min(0) @Parameter(description = "Идентификатор клиента") UUID id) {
        return ResponseEntity
                .ok(orderService.getAllOrderByCustomerID(id));
    }

    @Operation(summary = "Обновить данные заказа",
            description = "Обновить данные заказа по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> orderUpdateCourierAndStatus(@PathVariable("id") @Min(0) @Parameter(description = "Идентификатор заказа") UUID id,
                                                              @RequestBody @Parameter(description = "заказ") OrderDto order) {
        return ResponseEntity
                .ok(orderService.orderUpdateCourierAndStatus(id, order));
    }

    @Operation(summary = "Обновить статус заказа",
            description = "Обновить статус заказа по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PutMapping("/{id}/update/status")
    public ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") @Min(0) @Parameter(description = "Идентификатор заказа") UUID id,
                                                        @RequestParam("status") @Parameter(description = "статус") StatusOrders status) {
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

