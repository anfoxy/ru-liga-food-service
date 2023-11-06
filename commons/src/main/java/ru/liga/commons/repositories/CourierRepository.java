package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.model.Courier;
import ru.liga.commons.status.StatusCourier;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findAllByStatus(StatusCourier statusCourier);

}