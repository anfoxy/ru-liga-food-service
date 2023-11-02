package ru.liga.kitchen_service.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.RestaurantDto;
import ru.liga.kitchen_service.model.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring", uses = RestaurantMapper.class)
public interface RestaurantListMapper {

    List<Restaurant> toModelList(List<RestaurantDto> dtos);

    List<RestaurantDto> toDTOList(List<Restaurant> models);

}
