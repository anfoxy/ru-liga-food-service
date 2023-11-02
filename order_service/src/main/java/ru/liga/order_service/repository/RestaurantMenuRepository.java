package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.RestaurantMenuItem;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuItem, Long> {

}