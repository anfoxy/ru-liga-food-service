package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.RestaurantMenuItem;
import java.util.List;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuItem,Long> {
    List<RestaurantMenuItem> findAllByRestaurant_Id(Long id);

}