package ru.liga.delivery_service.controller;

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
import ru.liga.delivery_service.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Api для работы с доставкой")
@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery-service/delivery")
public class DeliveryController {

    private final DeliveryService orderService;

    @Operation(summary = "Получить активные или завершенные доставки")
    @GetMapping("courier/{id_courier}/status")
    public ResponseEntity<Object> getAllDelivery(@PathVariable("id_courier") Long id, @RequestParam("status") StatusOrders status) {
        return ResponseEntity
                .ok(orderService.getDeliveriesByStatus(id, status));
    }

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/order/{order_id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("order_id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.getDeliveryById(id));
    }

    @Operation(summary = "принять доставку")
    @PutMapping("/accepted/")
    public ResponseEntity<Object> acceptedDeliveryById(
            @RequestParam("id_order") Long idOrder,
            @RequestParam("id_courier") Long idCourier,
            HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.acceptedDelivery(idOrder, idCourier, request));
    }

    @Operation(summary = "отменить доставку")
    @PutMapping("/{order_id}/denied")
    public ResponseEntity<Object> deniedDeliveryById(@PathVariable("order_id") Long id, HttpServletRequest request) {
        return ResponseEntity
                .ok(orderService.deniedDelivery(id, request));
    }

    @Operation(summary = "Доставка заказа")
    @PutMapping("/{order_id}/delivering")
    public ResponseEntity<Object> deliveringDeliveryById(@PathVariable("order_id") Long id, HttpServletRequest request) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.deliveringDelivery(id, request));
    }

    @Operation(summary = "Доставка заказа")
    @PutMapping("/{order_id}/complete")
    public ResponseEntity<Object> completeDeliveryById(@PathVariable("order_id") Long id, HttpServletRequest request) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderService.completeDelivery(id, request));
    }

}
