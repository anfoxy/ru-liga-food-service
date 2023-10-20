package ru.liga.order_service.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.RestaurantMenuItem;

import java.util.stream.Collectors;

@Schema(description = "Пункт меню")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMenuItemDto {

    @Schema(description = "ID пункта меню")
    private Long id;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Изображение")
    private String image;

    @Schema(description = "Название")
    private String name;

    @Schema(description = "Цена")
    private Double price;

    @Schema(description = "статус")
    private String status;

    public RestaurantMenuItemDto(RestaurantMenuItem restaurantMenuItem) {
        this.id = restaurantMenuItem.getId();
        this.description = restaurantMenuItem.getDescription();
        this.image = restaurantMenuItem.getImage();
        this.name = restaurantMenuItem.getName();
        this.price = restaurantMenuItem.getPrice();
        this.status = restaurantMenuItem.getStatus();
    }

/*    public RestaurantMenuItem convertToEntity() {
        return new RestaurantMenuItem(id,null,name,price,description,image,status,null);
    }*/

}
