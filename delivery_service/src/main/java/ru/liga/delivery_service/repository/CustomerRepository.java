package ru.liga.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.delivery_service.model.Customer;


import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findAllByAddress(String id);

}