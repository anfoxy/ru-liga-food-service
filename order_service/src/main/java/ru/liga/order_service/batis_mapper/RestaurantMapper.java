package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusRestaurant;
import ru.liga.order_service.model.Order;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    Order findRestaurantById(Long id);

    List<Order> findAllRestaurantByStatus(StatusRestaurant status);

}
