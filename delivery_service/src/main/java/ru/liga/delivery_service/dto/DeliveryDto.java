package ru.liga.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Доставка")
@Data
@Accessors(chain = true)
public class DeliveryDto {

    @Schema(description = "ID заказа")
    private Long orderId;

    @Schema(description = "Ресторан")
    private RestaurantDTO restaurant;

    @Schema(description = "Клиент")
    private CustomerDTO customer;

    @Schema(description = "Оплата")
    private Double payment;

    @Schema(description = "Статус")
    private String orderAction;

}
