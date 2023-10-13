package ru.liga.kitchen_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Schema(description = "Заказ")
@Data
@Accessors(chain = true)
public class OrderDto {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Заказ")
    private List<MenuItemDto> menuItems;

}