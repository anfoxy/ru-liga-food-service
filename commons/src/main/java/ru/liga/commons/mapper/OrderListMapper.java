package ru.liga.commons.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.model.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderListMapper {

    List<Order> toModelList(List<OrderDto> dtos);

    List<OrderDto> toDTOList(List<Order> models);

}
