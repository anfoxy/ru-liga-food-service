package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.order_service.model.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    OrderItem findOrderItemById(Long id);

    List<OrderItem> findAllOrderItemByOrderId(Long id);

}
