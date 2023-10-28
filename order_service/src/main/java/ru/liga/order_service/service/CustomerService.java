package ru.liga.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.dto.OrdersResponseDto;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.mapper.DtoMapper;
import ru.liga.order_service.model.Customer;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.repository.CustomerRepository;
import ru.liga.order_service.repository.OrderRepository;
import ru.liga.order_service.repository.RestaurantRepository;

import java.time.ZonedDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final DtoMapper mapper;

    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Customer customerCreate(Customer customerRequest) throws CreationException {
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
        return customerRepository.save(customer);
    }

    public Customer customerUpdate(Long id, Customer customerRequest) throws ResourceNotFoundException {
        Customer customerResponse = getCustomerById(id);
        mapper.updateCustomerFromDto(customerRequest, customerResponse);
        customerResponse.setId(id);
        return customerRepository.save(customerResponse);
    }

}
