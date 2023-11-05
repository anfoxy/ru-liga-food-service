package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusRestaurant;

@Schema(description = "Ресторан")
@Data
@Setter
@Getter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    @Schema(description = "ID ресторана")
    private Long id;

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Статус")
    private StatusRestaurant status;

    @Schema(description = "Название")
    private String name;

}