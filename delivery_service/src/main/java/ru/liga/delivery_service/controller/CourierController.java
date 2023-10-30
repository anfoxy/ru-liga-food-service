package ru.liga.delivery_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.delivery_service.exception.CreationException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.model.Courier;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.delivery_service.service.DeliveryService;

@Tag(name = "Api для работы с курьерами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery/courier")
public class CourierController {

    final private CourierService courierService;

    @Operation(summary = "Получить курьера по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourierById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
        return ResponseEntity
                .ok(courierService.getCourierById(id));
    }

    @Operation(summary = "Создать нового курьера")
    @PostMapping("/create")
    public ResponseEntity<Object> createCourier(@RequestBody Courier courier) throws CreationException {
        return ResponseEntity
                .ok(courierService.courierCreate(courier));
    }

    @Operation(summary = "Обновить данные курьера по ID")
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateCourierById(@PathVariable("id") Long id, @RequestBody Courier courier) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(courierService.courierUpdate(id, courier));
    }

}

