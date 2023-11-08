package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.model.OrderItem;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findAllByOrder_Id(UUID id);

}