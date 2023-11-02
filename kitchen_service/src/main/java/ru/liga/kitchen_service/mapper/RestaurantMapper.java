package ru.liga.kitchen_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.kitchen_service.model.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantDto dto, @MappingTarget Restaurant entity);

    RestaurantDto toDTO(Restaurant model);

    Restaurant toModel(RestaurantDto dto);

}
