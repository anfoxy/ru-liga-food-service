package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByRestaurant_Id(Long id);
    List<Order> findAllByStatus(StatusOrders status);

}