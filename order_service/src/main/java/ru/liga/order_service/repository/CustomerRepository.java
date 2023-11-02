package ru.liga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.order_service.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}