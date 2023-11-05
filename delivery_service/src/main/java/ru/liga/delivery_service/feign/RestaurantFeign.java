package ru.liga.delivery_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.status.StatusOrders;

@FeignClient(name = "${restaurant.feign.name}", url = "${restaurant.url}")
public interface RestaurantFeign {

    @PutMapping("/kitchen-service/order/{id}/courier/{id_courier}/picking")
    ResponseEntity<Object> pickingOrderById(@PathVariable("id") Long id,
                                            @PathVariable("id_courier") Long id_courier);

}
