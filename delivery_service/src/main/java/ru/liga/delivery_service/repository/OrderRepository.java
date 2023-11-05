package ru.liga.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStatusAndCourier_Id(StatusOrders statusOrders, Long id);

}