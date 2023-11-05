package ru.liga.kitchen_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.kitchen_service.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrderDto dto, @MappingTarget Order entity);

    OrderDto toDTO(Order model);

    Order toModel(OrderDto dto);

}
