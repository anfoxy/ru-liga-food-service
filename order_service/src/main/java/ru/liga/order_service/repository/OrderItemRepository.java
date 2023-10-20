package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.OrderItem;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findAllByOrder_Id(Long id);

}