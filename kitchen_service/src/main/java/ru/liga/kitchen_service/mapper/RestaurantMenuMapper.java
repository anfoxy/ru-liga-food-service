package ru.liga.kitchen_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.RestaurantMenuItemDto;
import ru.liga.kitchen_service.model.RestaurantMenuItem;

@Mapper(componentModel = "spring")
public interface RestaurantMenuMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantMenuItemDto dto, @MappingTarget RestaurantMenuItem entity);

    RestaurantMenuItemDto toDTO(RestaurantMenuItem model);

    RestaurantMenuItem toModel(RestaurantMenuItemDto dto);

}
