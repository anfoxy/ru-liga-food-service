package ru.liga.kitchen_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.kitchen_service.model.Restaurant;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findAllByStatus(String status);
}