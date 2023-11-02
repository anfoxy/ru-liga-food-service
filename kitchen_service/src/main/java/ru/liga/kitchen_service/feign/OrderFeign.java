package ru.liga.kitchen_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.dto.dto_model.OrderDto;

@FeignClient(name = "${order.feign.name}", url = "${order.url}")
public interface OrderFeign {

    @PutMapping("/order/{id}/update/status")
    ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") Long id, @RequestParam("status") String status);

    @PutMapping("/order/{id}/update")
    ResponseEntity<Object> updateOrderById(@PathVariable("id") Long id, @RequestBody OrderDto order);

}
