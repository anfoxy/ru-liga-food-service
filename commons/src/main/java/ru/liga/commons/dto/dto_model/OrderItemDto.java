package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Пункт заказа")
@Data
@Setter
@Getter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    @Schema(description = "id")
    private UUID id;

    @Schema(description = "Цена")
    private Double price;

    @Schema(description = "Количество")
    private Integer quantity;

    @Schema(description = "пункт меню")
    private RestaurantMenuItemDto restaurantMenuItem;

}