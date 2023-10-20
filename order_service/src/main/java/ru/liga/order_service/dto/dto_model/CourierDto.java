package ru.liga.order_service.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.Courier;

@Schema(description = "Курьер")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CourierDto {

    @Schema(description = "ID курьера")
    private Long id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "Статус")
    private String status;

    @Schema(description = "Координаты")
    private String coordinates;

    public CourierDto(Courier courier) {
        this.id = courier.getId();
        this.phone = courier.getPhone();
        this.status = courier.getStatus();
        this.coordinates = courier.getCoordinates();
    }

  /*  public Courier convertToEntity() {
        return new Courier(id,phone,coordinates,status,null);
    }*/
}