package ru.liga.order_service.service;

import ru.liga.commons.dto.dto_model.CustomerDto;

public interface CustomerService {

    CustomerDto getCustomerById(Long id);

    CustomerDto customerCreate(CustomerDto customerRequest);

    CustomerDto customerUpdate(Long id, CustomerDto customerRequest);

}
