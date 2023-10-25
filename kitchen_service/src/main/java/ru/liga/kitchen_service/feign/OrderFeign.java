package ru.liga.kitchen_service.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.status.StatusOrders;


@FeignClient(name = "order-service", url = "http://localhost:8080")
public interface  OrderFeign {

    @GetMapping("/order/{id}")
    ResponseEntity<Object> getOrderById(@PathVariable("id") Long id);

    @GetMapping("order/status")
    ResponseEntity<Object> getAllOrderByStatus(@RequestParam("status") StatusOrders status);

    @PutMapping("/order/{id}/update/status")
    ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") Long id, @RequestParam("status") String status);

}
