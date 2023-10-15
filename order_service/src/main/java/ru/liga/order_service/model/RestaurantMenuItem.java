package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто пункта меню")
@Data
@Accessors(chain = true)
public class RestaurantMenuItem {

    @Schema(description = "ID пункта меню")
    private Long id;

    @Schema(description = "ресторан")
    private Restaurant restaurant;

    @Schema(description = "название")
    private String name;

    @Schema(description = "цена")
    private Double price;

    @Schema(description = "описание")
    private String description;

    @Schema(description = "изображение")
    private String image;

}
