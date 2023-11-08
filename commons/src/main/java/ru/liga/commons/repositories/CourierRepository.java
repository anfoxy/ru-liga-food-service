package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.model.Courier;
import ru.liga.commons.status.StatusCourier;

import java.util.List;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    List<Courier> findAllByStatus(StatusCourier statusCourier);

}