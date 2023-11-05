package ru.liga.order_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.OrderItemDto;
import ru.liga.order_service.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderItemFromDto(OrderItemDto dto, @MappingTarget OrderItem entity);

    OrderItemDto toDTO(OrderItem model);

    OrderItem toModel(OrderItemDto dto);

}
