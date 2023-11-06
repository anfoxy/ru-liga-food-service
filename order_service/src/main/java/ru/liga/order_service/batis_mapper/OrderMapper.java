package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.commons.model.Order;
import java.util.List;

@Mapper
public interface OrderMapper {

    Order findOrderById(Long id);

    List<Order> findAllOrderByRestaurantId(Long id);

}
