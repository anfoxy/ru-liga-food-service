package ru.liga.commons.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.RestaurantMenuItemDto;
import ru.liga.commons.model.RestaurantMenuItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = RestaurantMenuMapper.class)
public interface RestaurantMenuListMapper {

    List<RestaurantMenuItem> toModelList(List<RestaurantMenuItemDto> dtos);

    List<RestaurantMenuItemDto> toDTOList(List<RestaurantMenuItem> models);

}
