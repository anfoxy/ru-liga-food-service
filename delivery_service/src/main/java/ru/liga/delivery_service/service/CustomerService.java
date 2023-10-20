package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.model.Customer;
import ru.liga.delivery_service.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    public List<Customer> getCustomerByAddress(String address) {
        return customerRepository.findAllByAddress(address);
    }

}