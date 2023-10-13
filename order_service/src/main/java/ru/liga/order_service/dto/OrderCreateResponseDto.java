package ru.liga.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Ответ на создание заказа")
@Data
@Accessors(chain = true)
public class OrderCreateResponseDto {

    @Schema(description = "ID заказа")
    private Long id;

    @Schema(description = "Секретный url-адрес платежа")
    private String secretPaymentUrl;

    @Schema(description = "Предполагаемое время прибытия")
    private String estimatedTimeOfArrival;

}
