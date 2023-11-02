package ru.liga.kitchen_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.kitchen_service.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}