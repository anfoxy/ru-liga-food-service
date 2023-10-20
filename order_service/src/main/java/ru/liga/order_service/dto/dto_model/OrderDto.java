package ru.liga.order_service.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.Order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Заказ")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDto {

    @Schema(description = "ID заказа")
    private Long id;

    @Schema(description = "Клиент")
    private CustomerDto customer;

    @Schema(description = "Ресторан")
    private RestaurantDto restaurant;

    @Schema(description = "Статус")
    private String status;

    @Schema(description = "Курьер")
    private CourierDto courier;

    @Schema(description = "Дата заказа")
    private ZonedDateTime timestamp;

    @Schema(description = "Список пунктов заказа")
    private List<OrderItemDto> orderItems;

    public OrderDto(Order c) {
        this.id = c.getId();
        this.customer = new CustomerDto(c.getCustomer());
        this.restaurant = new RestaurantDto(c.getRestaurant());
        this.status = c.getStatus();
        this.timestamp = c.getTimestamp();
        this.orderItems = c.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        if (c.getCourier() != null) {
            this.courier = new CourierDto(c.getCourier());
        }
    }

/*    public Order convertToEntity() {
        return new Order(
                id,
                customer.convertToEntity(),
                restaurant.convertToEntity(),
                courier.convertToEntity(),
                status,
                timestamp,
                orderItems.stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList()));
    }*/

}