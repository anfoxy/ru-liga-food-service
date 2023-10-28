package ru.liga.order_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.order_service.model.Customer;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.OrderItem;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(Order dto, @MappingTarget Order entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderItemFromDto(OrderItem dto, @MappingTarget OrderItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(Customer dto, @MappingTarget Customer entity);

}
