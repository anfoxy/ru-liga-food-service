package ru.liga.order_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Schema(description = "Создание заказа")
@Data
@Accessors(chain = true)
public class OrderCreateRequestDto {

    @Schema(description = "ID ресторана")
    private Long restaurantId;

    @Schema(description = "Заказ")
    private List<MenuItemDto> menuItems;

}
