package ru.liga.delivery_service.service;

import org.springframework.stereotype.Service;
import ru.liga.delivery_service.dto.DeliveriesResponseDto;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import java.util.ArrayList;

@Service
public class DeliveryService {

    public DeliveriesResponseDto getDeliveriesByStatus(String status) {
        DeliveriesResponseDto deliveriesResponseDto = new DeliveriesResponseDto();
        deliveriesResponseDto.setDelivery(new ArrayList<>());
        return deliveriesResponseDto;
    }

    public boolean getDeliveriesByStatus(Long id, String orderAction) throws ResourceNotFoundException {
        if (id < 0) {
            throw new ResourceNotFoundException();
        }
        return true;
    }

}
