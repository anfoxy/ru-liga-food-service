package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;
import java.util.UUID;

@Schema(description = "Создание заказа")
@Data
@Accessors(chain = true)
public class OrderCreateRequestDto {

    @Schema(description = "ID ресторана")
    private UUID restaurant;

    @Schema(description = "ID клиента")
    private UUID customer;

    @Schema(description = "Заказ")
    private List<OrderItemCreateRequestDto> orderItems;

}
