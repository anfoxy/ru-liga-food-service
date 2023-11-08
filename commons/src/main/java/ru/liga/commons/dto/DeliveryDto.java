package ru.liga.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusOrders;

import java.util.UUID;

@Schema(description = "Доставка")
@Data
@Builder
@Accessors(chain = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {

    @Schema(description = "ID заказа")
    private UUID orderId;

    @Schema(description = "ID курьера")
    private UUID courierId;

    @Schema(description = "Ресторан")
    private RestaurantForDeliveryDto restaurant;

    @Schema(description = "Клиент")
    private CustomerForDeliveryDto customer;

    @Schema(description = "Оплата")
    private Double payment;

    @Schema(description = "Статус")
    private StatusOrders orderAction;

}
