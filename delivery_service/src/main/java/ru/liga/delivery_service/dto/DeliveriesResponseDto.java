package ru.liga.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.delivery_service.model.Order;

import java.util.List;

@Schema(description = "Список доставок")
@Data
@Accessors(chain = true)
public class DeliveriesResponseDto {

    @Schema(description = "Список заказов")
    private List<DeliveryDto> delivery;

    @Schema(description = "Индекс страницы")
    private int pageIndex;

    @Schema(description = "Количество страниц")
    private int pageCount;

}
