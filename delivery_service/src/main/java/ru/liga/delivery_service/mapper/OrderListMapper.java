package ru.liga.delivery_service.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.delivery_service.model.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderListMapper {

    List<Order> toModelList(List<OrderDto> dtos);

    List<OrderDto> toDTOList(List<Order> models);

}
