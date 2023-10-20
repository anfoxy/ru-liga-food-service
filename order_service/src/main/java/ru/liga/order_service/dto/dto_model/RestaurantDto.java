package ru.liga.order_service.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.OrderItem;
import ru.liga.order_service.model.Restaurant;

@Schema(description = "Ресторан")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    @Schema(description = "ID ресторана")
    private Long id;

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Статус")
    private String status;


    @Schema(description = "Название")
    private String name;

    public RestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.address = restaurant.getAddress();
        this.status = restaurant.getStatus();
        this.name = restaurant.getName();

    }
    public Restaurant convertToEntity() {
        return new Restaurant( id,address,status,name,null,null);
    }

}