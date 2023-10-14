package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто ресторана")
@Data
@Accessors(chain = true)
public class Restaurant {

    @Schema(description = "ID ресторана")
    private Long id;

    @Schema(description = "адрес")
    private String address;

    @Schema(description = "статус")
    private String status;

}
