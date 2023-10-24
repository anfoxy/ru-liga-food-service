package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.Restaurant;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    Restaurant findRestaurantById(Long id);

    List<Restaurant> findAllRestaurantByStatus(StatusRestaurant status);

}
