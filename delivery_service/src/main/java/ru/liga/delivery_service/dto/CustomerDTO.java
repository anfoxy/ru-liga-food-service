package ru.liga.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Заказчик")
@Data
@Accessors(chain = true)
public class CustomerDTO {

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Расстояние")
    private Double distance;

}
