package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusCourier;

import java.util.UUID;

@Schema(description = "Курьер")
@Data
@Setter
@Getter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CourierDto {

    @Schema(description = "ID курьера")
    private UUID id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "Статус")
    private StatusCourier status;

    @Schema(description = "Координаты")
    private String coordinates;

}