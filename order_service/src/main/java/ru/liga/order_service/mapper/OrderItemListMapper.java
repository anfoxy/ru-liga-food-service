package ru.liga.order_service.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.order_service.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderItemListMapper {

    List<OrderItem> toModelList(List<OrderItemDto> dtos);

    List<OrderItemDto> toDTOList(List<OrderItem> models);

}
