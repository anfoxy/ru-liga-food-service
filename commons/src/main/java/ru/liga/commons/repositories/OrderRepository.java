package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.commons.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByRestaurant_Id(Long id);

    List<Order> findAllByCustomer_Id(Long id);

    List<Order> findAllByStatusAndRestaurant_Id(StatusOrders status, Long id);

    List<Order> findAllByStatus(StatusOrders status);

    List<Order> findAllByStatusAndCourier_Id(StatusOrders statusOrders, Long id);


}