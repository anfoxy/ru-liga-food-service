package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Ресторан")
@Data
@Accessors(chain = true)
public class RestaurantDto {

    @Schema(description = "ID ресторана")
    private Long id;

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Статус")
    private String status;

    @Schema(description = "Название")
    private String name;

}