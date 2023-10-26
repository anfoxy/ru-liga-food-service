package ru.liga.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.delivery_service.model.Courier;


public interface CourierRepository extends JpaRepository<Courier,Long> {

}