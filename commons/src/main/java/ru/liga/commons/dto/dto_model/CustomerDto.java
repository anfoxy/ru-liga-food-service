package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Курьер")
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerDto {

    @Schema(description = "ID курьера")
    private UUID id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "email")
    private String email;

    @Schema(description = "адрес")
    private String address;

}