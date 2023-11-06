package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.commons.model.Order;
import java.util.List;

@Schema(description = "Список заказов")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrdersResponseDto {

    @Schema(description = "Список заказов")
    private List<Order> orders;

    @Schema(description = "Индекс страницы")
    private Integer pageIndex;

    @Schema(description = "Количество страниц")
    private Integer pageCount;

}