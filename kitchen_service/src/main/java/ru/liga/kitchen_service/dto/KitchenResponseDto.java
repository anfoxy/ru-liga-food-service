package ru.liga.kitchen_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Schema(description = "Список заказов")
@Data
@Accessors(chain = true)
public class KitchenResponseDto {

    @Schema(description = "Список заказов")
    private List<OrderDto> orders;

    @Schema(description = "Индекс страницы")
    private Integer pageIndex;

    @Schema(description = "Количество страниц")
    private Integer pageCount;

}
