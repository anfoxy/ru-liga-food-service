package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.Restaurant;
import ru.liga.commons.status.StatusRestaurant;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findAllByStatus(StatusRestaurant status);

}