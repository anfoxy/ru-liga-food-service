package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.status.StatusOrders;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.exception.CreationException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.mapper.DtoMapper;
import ru.liga.delivery_service.model.Courier;
import ru.liga.delivery_service.repository.CourierRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final DtoMapper mapper;

    public Courier getCourierById(Long id) throws ResourceNotFoundException {
        return courierRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Courier courierCreate(Courier courierRequest) throws CreationException {
        if (courierRequest.getCoordinates() == null
                || courierRequest.getPhone() == null
        ) {
            throw new CreationException("Bad request");
        }
        Courier courier = Courier
                .builder()
                .coordinates(courierRequest.getCoordinates())
                .phone(courierRequest.getPhone())
                .status(StatusCourier.COURIER_NOT_ACTIVE)
                .build();
        return courierRepository.save(courier);
    }

    public Courier courierUpdate(Long id, Courier courierRequest) throws ResourceNotFoundException {
        Courier courierResponse = getCourierById(id);
        mapper.updateCourierFromDto(courierRequest, courierResponse);
        courierResponse.setId(id);
        return courierRepository.save(courierResponse);
    }

}
