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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.order_service.dto.OrderItemCreateRequestDto;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.service.OrderItemService;

@Tag(name = "Api для работы с пунктом заказа")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order_item")
public class OrderItemController {

    final private OrderItemService orderItemMenuService;

    @Operation(summary = "Получить пункт заказа по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderItemById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderItemMenuService.getOrderItemById(id));
    }

    @Operation(summary = "Получить пункт заказа по id заказа")
    @GetMapping("/order_id/{order_id}")
    public ResponseEntity<Object> getOrderItemByOrderId(@PathVariable("order_id") Long id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderItemMenuService.getOrderItemByOrderId(id));
    }

    @Operation(summary = "добавить пункт заказа")
    @PostMapping("/create")
    public ResponseEntity<Object> createOrderItem(@RequestBody OrderItemCreateRequestDto orderItem) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(orderItemMenuService.createOrderItem(orderItem));
    }

    @Operation(summary = "удалить пункт заказа по id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        orderItemMenuService.deleteOrderItemById(id);
        return ResponseEntity
                .ok("OrderItem with id:"+id+" deleted");
    }

}

