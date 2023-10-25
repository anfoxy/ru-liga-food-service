package ru.liga.kitchen_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.kitchen_service.model.Order;

import java.util.List;

@Schema(description = "Заказ")
@Data
@Accessors(chain = true)
public class OrderListDto {

    @Schema(description = "Заказ")
    private List<Order> orderList;

}