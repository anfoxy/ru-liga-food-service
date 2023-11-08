package ru.liga.delivery_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.delivery_service.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Tag(name = "Api для работы с доставкой")
@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery-service/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Получить доставки, которые можно принять")
    @GetMapping("/courier/{courier_id}/active")
    public ResponseEntity<Object> getAllDelivery(@PathVariable("courier_id") UUID id) {
        return ResponseEntity
                .ok(deliveryService.getActiveDeliveries(id));
    }

    @Operation(summary = "Получить доставки по id")
    @GetMapping("/{order_id}")
    public ResponseEntity<Object> getDeliveryById(@PathVariable("order_id") UUID id) {
        return ResponseEntity
                .ok(deliveryService.getDeliveryById(id));
    }

    @Operation(summary = "Принять доставку")
    @PostMapping("/accepted")
    public ResponseEntity<Object> acceptedDeliveryById(
            @RequestParam("id_order") UUID idOrder,
            @RequestParam("id_courier") UUID idCourier,
            HttpServletRequest request) {
        return ResponseEntity
                .ok(deliveryService.acceptedDelivery(idOrder, idCourier, request));
    }

    @Operation(summary = "Отменить доставку")
    @PostMapping("/{order_id}/denied")
    public ResponseEntity<Object> deniedDeliveryById(@PathVariable("order_id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(deliveryService.deniedDelivery(id, request));
    }

    @Operation(summary = "Доставка заказа")
    @PostMapping("/{order_id}/delivering")
    public ResponseEntity<Object> deliveringDeliveryById(@PathVariable("order_id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(deliveryService.deliveringDelivery(id, request));
    }

    @Operation(summary = "Завершить доставку заказа")
    @PostMapping("/{order_id}/complete")
    public ResponseEntity<Object> completeDeliveryById(@PathVariable("order_id") UUID id, HttpServletRequest request) {
        return ResponseEntity
                .ok(deliveryService.completeDelivery(id, request));
    }

}
