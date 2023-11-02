package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}