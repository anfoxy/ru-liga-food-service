package ru.liga.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.commons.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}