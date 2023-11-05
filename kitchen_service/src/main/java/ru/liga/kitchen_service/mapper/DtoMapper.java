package ru.liga.kitchen_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.kitchen_service.model.Restaurant;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(Restaurant dto, @MappingTarget Restaurant entity);

}
