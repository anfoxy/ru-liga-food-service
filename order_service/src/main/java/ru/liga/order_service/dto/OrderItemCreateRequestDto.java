package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Создание заказа")
@Data
@Accessors(chain = true)
public class OrderItemCreateRequestDto {

    @Schema(description = "Количество")
    private Integer quantity;

    @Schema(description = "пункт меню")
    private UUID restaurantMenuItem;

    @Schema(description = "заказ")
    private UUID order;

}
