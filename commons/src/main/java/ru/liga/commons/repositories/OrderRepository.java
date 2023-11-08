package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.status.StatusOrders;
import ru.liga.commons.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByRestaurant_Id(UUID id);

    List<Order> findAllByCustomer_Id(UUID id);

    List<Order> findAllByStatusAndRestaurant_Id(StatusOrders status, UUID id);

    List<Order> findAllByStatus(StatusOrders status);

    List<Order> findAllByStatusAndCourier_Id(StatusOrders statusOrders, UUID id);


}