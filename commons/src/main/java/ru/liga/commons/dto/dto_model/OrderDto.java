package ru.liga.commons.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.liga.commons.status.StatusOrders;
import java.time.ZonedDateTime;
import java.util.List;

@Schema(description = "Заказ")
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class OrderDto {

    @Schema(description = "ID заказа")
    private Long id;

    @Schema(description = "Клиент")
    private CustomerDto customer;

    @Schema(description = "Ресторан")
    private RestaurantDto restaurant;

    @Schema(description = "Статус")
    private StatusOrders status;

    @Schema(description = "Курьер")
    private CourierDto courier;

    @Schema(description = "Дата заказа")
    private ZonedDateTime timestamp;

    @Schema(description = "Список пунктов заказа")
    private List<OrderItemDto> orderItems;

}