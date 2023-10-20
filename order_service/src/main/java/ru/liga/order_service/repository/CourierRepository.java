package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.Courier;

public interface CourierRepository extends JpaRepository<Courier,Long> {

}