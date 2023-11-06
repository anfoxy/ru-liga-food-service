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

    @Schema(description = "Статус")
    private String status;

    @Schema(description = "Id курьера")
    private Long courierID;

    @Schema(description = "Id заказа")
    private Long orderID;

}
