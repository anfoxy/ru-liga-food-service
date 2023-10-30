package ru.liga.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.order_service.exception.CreationException;
import ru.liga.order_service.exception.ResourceNotFoundException;
import ru.liga.order_service.model.Customer;
import ru.liga.order_service.service.CustomerService;


@Tag(name = "Api для работы с клиентами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order/customer")
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

    @Operation(summary = "Создать нового клиента")
    @PostMapping("/create")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) throws ResourceNotFoundException, CreationException {
        return ResponseEntity
                .ok(customerService.customerCreate(customer));
    }

    @Operation(summary = "Обновить данные клиента по ID")
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateCustomerById(@PathVariable("id") Long id, @RequestBody Customer customer) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(customerService.customerUpdate(id, customer));
    }

}

