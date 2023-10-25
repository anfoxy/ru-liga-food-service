package ru.liga.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusOrders;

@Schema(description = "Доставка")
@Data
@Builder
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
    private StatusOrders orderAction;
}
