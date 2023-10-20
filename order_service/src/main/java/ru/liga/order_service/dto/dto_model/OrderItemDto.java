package ru.liga.order_service.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.OrderItem;

@Schema(description = "Пункт заказа")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Цена")
    private Double price;

    @Schema(description = "Количество")
    private Integer quantity;

    @Schema(description = "пункт меню")
    private RestaurantMenuItemDto restaurantMenuItem;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
        this.restaurantMenuItem = new RestaurantMenuItemDto(orderItem.getRestaurantMenuItem());
    }

    /*public OrderItem convertToEntity() {
        return new OrderItem(id,null,null,price,quantity);
    }*/
}