package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.order_service.service.OrderItemService;

@Tag(name = "Api для работы с пунктом заказа")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service/order_item")
public class OrderItemController {

    final private OrderItemService orderItemMenuService;

    @Operation(summary = "Получить пункт заказа по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderItemById(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderItemMenuService.getOrderItemById(id));
    }

    @Operation(summary = "Получить пункт заказа по id заказа")
    @GetMapping("/order/{order_id}")
    public ResponseEntity<Object> getAllOrderItemByOrderId(@PathVariable("order_id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderItemMenuService.getAllOrderItemByOrderId(id));
    }

    @Operation(summary = "добавить пункт заказа")
    @PostMapping("/create")
    public ResponseEntity<Object> createOrderItem(@RequestBody OrderItemCreateRequestDto orderItem) {
        return ResponseEntity
                .ok(orderItemMenuService.createOrderItem(orderItem));
    }

    @Operation(summary = "Обновить данные заказа по ID")
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateOrderItemMenuById(@PathVariable("id") Long id, @RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity
                .ok(orderItemMenuService.orderItemMenuUpdate(id, orderItemDto));
    }

    @Operation(summary = "удалить пункт заказа по id")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteOrderItem(@PathVariable(value = "id") Long id) {
        orderItemMenuService.deleteOrderItemById(id);
        return ResponseEntity
                .ok("OrderItem with id:" + id + " deleted");
    }

}

