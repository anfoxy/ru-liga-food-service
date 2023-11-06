package ru.liga.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Schema(description = "Ресторан")
@Data
@Builder
@Accessors(chain = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantForDeliveryDto {

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Расстояние")
    private Double distance;

}
