package ru.liga.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.status.StatusCourier;
import ru.liga.delivery_service.model.Courier;

import java.util.List;


public interface CourierRepository extends JpaRepository<Courier,Long> {


    List<Courier> findAllByIdAndStatus(Long id, StatusCourier statusCourier);
}