package ru.liga.order_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Дто заказчика")
@Data
@Accessors(chain = true)
public class Customer {

    @Schema(description = "ID заказчика")
    private Long id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "email")
    private String email;

    @Schema(description = "адрес")
    private String address;

}
