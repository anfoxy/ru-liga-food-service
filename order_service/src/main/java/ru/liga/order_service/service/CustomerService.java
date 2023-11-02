package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.mapper.CustomerMapper;
import ru.liga.order_service.model.Customer;
import ru.liga.order_service.repository.CustomerRepository;
import ru.liga.commons.dto.dto_model.CustomerDto;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerDto getCustomerById(Long id) {
        return mapper
                .toDTO(customerRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new));
    }

    public CustomerDto customerCreate(CustomerDto customerRequest) {
        if (customerRequest.getAddress() == null
                || customerRequest.getPhone() == null
                || customerRequest.getEmail() == null) {
            throw new CreationException("Bad request");
        }
        Customer customer = Customer
                .builder()
                .address(customerRequest.getAddress())
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .build();
        return mapper
                .toDTO(customerRepository
                        .save(customer));
    }

    public CustomerDto customerUpdate(Long id, CustomerDto customerRequest) {
        Customer customerResponse = mapper.toModel(getCustomerById(id));
        mapper.updateCustomerFromDto(customerRequest, customerResponse);
        customerResponse.setId(id);
        return mapper
                .toDTO(customerRepository
                        .save(customerResponse));
    }

}
