package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто заказа")
@Data
@Accessors(chain = true)
public class Order {

    @Schema(description = "ID заказа")
    private Long Id;

    @Schema(description = "заказчик")
    private Customer customer;

    @Schema(description = "ресторан")
    private Restaurant restaurant;

    @Schema(description = "статус")
    private String status;

    @Schema(description = "курьер")
    private Courier courier;

    @Schema(description = "Дата заказа")
    private String timestamp;

}
