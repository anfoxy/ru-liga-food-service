package ru.liga.delivery_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.status.StatusOrders;

@FeignClient(name = "${restaurant.feign.name}", url = "${restaurant.url}")
public interface RestaurantFeign {

    @PutMapping("/kitchen-service/order/courier/confirm")
    ResponseEntity<Object> pickingOrderById(@RequestBody ConfirmCourierDto confirmCourierDto);

}
