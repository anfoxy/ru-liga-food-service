package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.model.RestaurantMenuItem;

import java.util.List;
import java.util.UUID;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuItem, UUID> {

    List<RestaurantMenuItem> findAllByRestaurant_Id(UUID id);

}