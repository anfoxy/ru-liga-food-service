package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.order_service.batis_mapper.CourierMapper;
import ru.liga.order_service.batis_mapper.CustomerMapper;
import ru.liga.order_service.batis_mapper.OrderItemMapper;
import ru.liga.order_service.batis_mapper.OrderMapper;
import ru.liga.order_service.batis_mapper.RestaurantMapper;
import ru.liga.order_service.batis_mapper.RestaurantMenuMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my_batis")
public class myBatisController {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CourierMapper courierMapper;
    private final CustomerMapper customerMapper;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantMenuMapper restaurantMenuMapper;

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrderByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderMapper.findOrderById(id));
    }

    @Operation(summary = "Получить заказ по id ресторана")
    @GetMapping("/order/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getOrderByRestaurantIDMyBatis(@PathVariable("restaurant_id") Long restaurant_id) {
        return ResponseEntity
                .ok(orderMapper.findAllOrderByRestaurantId(restaurant_id));
    }

    @Operation(summary = "Получить курьера по ID")
    @GetMapping("/courier/{id}")
    public ResponseEntity<Object> getCourierByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(courierMapper.findCourierById(id));
    }

    @Operation(summary = "Получить курьера по статусу")
    @GetMapping("/courier/status/{status}")
    public ResponseEntity<Object> getCourierByStatusIDMyBatis(@PathVariable("status") StatusCourier statusCourier) {
        return ResponseEntity
                .ok(courierMapper.findAllCourierByStatus(statusCourier));
    }

    @Operation(summary = "Получить клиента по ID")
    @GetMapping("/customer/{id}")
    public ResponseEntity<Object> getCustomerByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(customerMapper.findCustomerById(id));
    }

    @Operation(summary = "Получить клиента по номеру телефона")
    @GetMapping("/customer/phone/{phone}")
    public ResponseEntity<Object> getCustomerByStatusIDMyBatis(@PathVariable("phone") String phone) {
        return ResponseEntity
                .ok(customerMapper.findAllCustomerByPhone(phone));
    }

    @Operation(summary = "Получить ресторан по ID")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Object> getRestaurantByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantMapper.findRestaurantById(id));
    }

    @Operation(summary = "Получить ресторан по статусу")
    @GetMapping("/restaurant/status/{status}")
    public ResponseEntity<Object> getRestaurantByStatusIDMyBatis(@PathVariable("status") StatusRestaurant statusRestaurant) {
        return ResponseEntity
                .ok(restaurantMapper.findAllRestaurantByStatus(statusRestaurant));
    }

    @Operation(summary = "Получить пункт заказа по ID")
    @GetMapping("/order_item/{id}")
    public ResponseEntity<Object> getOrderItemByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(orderItemMapper.findOrderItemById(id));
    }

    @Operation(summary = "Получить пункт заказа по id ресторана")
    @GetMapping("/order_item/order/{order_id}")
    public ResponseEntity<Object> getOrderItemByOrderIDMyBatis(@PathVariable("order_id") Long order_id) {
        return ResponseEntity
                .ok(orderItemMapper.findAllOrderItemByOrderId(order_id));
    }

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/restaurant_menu/{id}")
    public ResponseEntity<Object> getRestaurantMenuByIdMyBatis(@PathVariable("id") Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(restaurantMenuMapper.findRestaurantMenuById(id));
    }

    @Operation(summary = "Получить заказ по id ресторана")
    @GetMapping("/restaurant_menu/restaurant/{restaurant_id}")
    public ResponseEntity<Object> getRestaurantMenuByRestaurantIDMyBatis(@PathVariable("restaurant_id") Long restaurant_id) {
        return ResponseEntity
                .ok(restaurantMenuMapper.findAllRestaurantMenuByRestaurantId(restaurant_id));
    }

}
