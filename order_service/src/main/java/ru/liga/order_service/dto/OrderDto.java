package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.Courier;
import ru.liga.order_service.model.Customer;
import ru.liga.order_service.model.Restaurant;
import java.util.List;

@Schema(description = "Заказ")
@Data
@Accessors(chain = true)
public class OrderDto {

    @Schema(description = "ID заказа")
    private Long id;

    @Schema(description = "Клиент")
    private Customer customer;

    @Schema(description = "Ресторан")
    private Restaurant restaurant;

    @Schema(description = "Статус")
    private String status;

    @Schema(description = "Курьер")
    private Courier courier;

    @Schema(description = "Дата заказа")
    private String timestamp;

    @Schema(description = "Список пунктов заказа")
    private List<ItemDto> items;

}