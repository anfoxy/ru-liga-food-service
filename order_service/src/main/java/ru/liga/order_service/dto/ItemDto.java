package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Пункт заказа")
@Data
@Accessors(chain = true)
public class ItemDto {

    @Schema(description = "Цена")
    private String price;

    @Schema(description = "Количество")
    private Integer quantity;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Изображение")
    private String image;

}