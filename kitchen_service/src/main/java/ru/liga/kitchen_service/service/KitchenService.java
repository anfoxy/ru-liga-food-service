package ru.liga.kitchen_service.service;

import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.dto.KitchenResponseDto;
import ru.liga.kitchen_service.exception.ResourceNotFoundException;
import java.util.ArrayList;


@Service
public class KitchenService {

    public KitchenResponseDto getDeliveriesByStatus(String status) throws ResourceNotFoundException {
        if (status.equals("denied")) {
            throw new ResourceNotFoundException();
        }
        KitchenResponseDto kitchenResponseDto = new KitchenResponseDto();
        kitchenResponseDto.setOrders(new ArrayList<>());
        return kitchenResponseDto;
    }

}
