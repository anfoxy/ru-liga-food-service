package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusRestaurantMenu;

import java.util.UUID;

@Schema(description = "Пункт меню")
@Data
@Setter
@Getter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMenuItemDto {

    @Schema(description = "ID пункта меню")
    private UUID id;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Изображение")
    private String image;

    @Schema(description = "Название")
    private String name;

    @Schema(description = "Цена")
    private Double price;

    @Schema(description = "статус")
    private StatusRestaurantMenu status;

    @Schema(description = "Ресторан")
    private RestaurantDto restaurant;

}
