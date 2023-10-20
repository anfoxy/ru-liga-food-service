package ru.liga.order_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.order_service.dto.dto_model.OrderDto;
import ru.liga.order_service.model.Order;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBooksFromDto(OrderDto dto, @MappingTarget Order entity);

}
