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

import java.util.UUID;

@Schema(description = "Доставка")
@Data
@Builder
@Accessors(chain = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmCourierDto {

    @Schema(description = "Id курьера")
    private UUID courierID;

    @Schema(description = "Id заказа")
    private UUID orderID;

}
