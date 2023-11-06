package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.CourierListMapper;
import ru.liga.commons.mapper.CourierMapper;
import ru.liga.commons.model.Courier;
import ru.liga.commons.repositories.CourierRepository;
import ru.liga.delivery_service.service.impl.MessageSenderCourier;

import java.util.List;


public interface CourierService {

    public CourierDto getCourierById(Long id);

    public List<CourierDto> getCourierByStatusActive();

    public CourierDto courierCreate(CourierDto courierRequest);

    public CourierDto getClosestCourier(String restaurantAddress, String district);

    public CourierDto courierUpdateById(Long id, CourierDto courierRequest);

    public void courierUpdateStatusById(Long id, StatusCourier statusCourier);

}
