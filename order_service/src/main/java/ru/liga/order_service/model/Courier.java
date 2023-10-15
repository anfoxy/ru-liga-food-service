package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто курьера")
@Data
@Accessors(chain = true)
public class Courier {

    @Schema(description = "ID курьера")
    private Long id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "координаты")
    private String coordinates;

    @Schema(description = "статус")
    private String status;

}
