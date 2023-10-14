package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто пункта заказа")
@Data
@Accessors(chain = true)
public class OrderItem {

    @Schema(description = "ID пункта заказа")
    private Long id;

    @Schema(description = "заказ")
    private Order order;

    @Schema(description = "пункт меню")
    private RestaurantMenuItem restaurantMenuItem;

    @Schema(description = "цена")
    private Double price;

    @Schema(description = "количество")
    private Integer quantity;

}
