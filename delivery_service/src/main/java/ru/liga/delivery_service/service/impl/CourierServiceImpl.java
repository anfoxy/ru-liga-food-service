package ru.liga.delivery_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.exception.CreationException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.commons.mapper.CourierListMapper;
import ru.liga.commons.mapper.CourierMapper;
import ru.liga.commons.model.Courier;
import ru.liga.commons.model.Order;
import ru.liga.commons.repositories.CourierRepository;
import ru.liga.commons.repositories.OrderRepository;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.util.DistanceCalculator;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.commons.util.LocalityDeterminant;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final CourierMapper mapper;
    private final CourierListMapper listMapper;
    private final DistanceCalculator distanceCalculator;
    private final LocalityDeterminant localityDeterminant;

    @Override
    public CourierDto getCourierById(UUID id) {
        return mapper.toDTO(courierRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<CourierDto> getCourierByStatusActive() {
        return listMapper.toDTOList(courierRepository
                .findAllByStatus(StatusCourier.COURIER_ACTIVE));
    }

    @Override
    public CourierDto getClosestCourier(String restaurantAddress, String district) {
        List<CourierDto> couriers = getCourierByStatusActive();
        log.info("CourierService: getCourierByStatusActive = " + couriers);
        CourierDto closestCourier = null;
        double minDistance = Double.MAX_VALUE;

        for (CourierDto courier : couriers) {
            String districtCourier = localityDeterminant.getLocation(courier.getCoordinates());
            if (!district.equals(districtCourier)) {
                log.info("CourierService: districtCourier = " + districtCourier + "district order" + district);
                continue;
            }
            double distance = distanceCalculator.calculator(courier.getCoordinates(), restaurantAddress);
            if (distance < minDistance) {
                minDistance = distance;
                closestCourier = courier;
            }
        }

        return closestCourier;
    }

    @Override
    public void updateCourierStatusById(UUID id, StatusCourier statusCourier) {
        Courier courierResponse = mapper.toModel(getCourierById(id));
        courierResponse.setStatus(statusCourier);
        mapper.toDTO(courierRepository
                .save(courierResponse));
    }



}
