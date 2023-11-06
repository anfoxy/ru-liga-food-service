package ru.liga.delivery_service.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.liga.commons.dto.ConfirmCourierDto;

@FeignClient(name = "${restaurant.feign.name}", url = "${restaurant.url}")
public interface RestaurantFeign {

    @PutMapping("/kitchen-service/order/courier/confirm")
    @Headers("Authorization: {token}")
    ResponseEntity<Object> pickingOrderById(@RequestBody ConfirmCourierDto confirmCourierDto,
                                            @RequestHeader("Authorization") String token);

}
