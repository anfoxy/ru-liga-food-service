package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.RestaurantMenuItem;

import java.util.List;

@Mapper
public interface RestaurantMenuMapper {

    RestaurantMenuItem findRestaurantMenuById(Long id);

    List<RestaurantMenuItem> findAllRestaurantMenuByRestaurantId(Long id);

}
