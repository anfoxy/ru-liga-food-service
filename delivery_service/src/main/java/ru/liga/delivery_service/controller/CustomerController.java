package ru.liga.delivery_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.service.CustomerService;

@Tag(name = "Api для работы с клиентами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    final private CustomerService customerService;

    @Operation(summary = "Получить клиента по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(customerService.getCustomerById(id));
    }

    @Operation(summary = "Получить клиента по адресу")
    @GetMapping("/address/{address}")
    public ResponseEntity<Object> getCustomerByAddress(@PathVariable("address") String address) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(customerService.getCustomerByAddress(address));
    }

}

