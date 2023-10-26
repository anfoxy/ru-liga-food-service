package ru.liga.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Ресторан")
@Data
@Builder
@Accessors(chain = true)
public class RestaurantDTO {

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Расстояние")
    private Double distance;

}
