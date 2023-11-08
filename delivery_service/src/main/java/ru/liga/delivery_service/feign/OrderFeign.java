package ru.liga.delivery_service.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;


@FeignClient(name = "${order.feign.name}", url = "${order.url}")
public interface OrderFeign {

    @PutMapping("/order-service/order/{id}/update/status")
    @Headers("Authorization: {token}")
    ResponseEntity<Object> updateOrderStatusById(@PathVariable("id") UUID id,
                                                 @RequestParam("status") String status,
                                                 @RequestHeader("Authorization") String token);

}
