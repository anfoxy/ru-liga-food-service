package ru.liga.order_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.CustomerDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.CustomerMapper;
import ru.liga.commons.model.Customer;
import ru.liga.commons.repositories.CustomerRepository;
import ru.liga.order_service.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

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
