package ru.liga.kitchen_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Пункт меню")
@Data
@Accessors(chain = true)
public class MenuItemDto {

    @Schema(description = "ID пункта меню")
    private Long menuItemId;

    @Schema(description = "Количество")
    private Integer quantity;

}
