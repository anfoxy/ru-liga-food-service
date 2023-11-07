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
import ru.liga.commons.repositories.CourierRepository;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.util.DistanceCalculator;
import ru.liga.delivery_service.service.CourierService;
import ru.liga.commons.util.LocalityDeterminant;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper mapper;
    private final CourierListMapper listMapper;
    private final DistanceCalculator distanceCalculator;
    private final LocalityDeterminant localityDeterminant;

    public CourierDto getCourierById(Long id) {
        return mapper.toDTO(courierRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    public List<CourierDto> getCourierByStatusActive() {
        return listMapper.toDTOList(courierRepository
                .findAllByStatus(StatusCourier.COURIER_ACTIVE));
    }

    public CourierDto createCourier(CourierDto courierRequest) {
        if (courierRequest.getCoordinates() == null
                || courierRequest.getPhone() == null) {
            throw new CreationException("Bad request");
        }

        Courier courier = Courier
                .builder()
                .coordinates(courierRequest.getCoordinates())
                .phone(courierRequest.getPhone())
                .status(StatusCourier.COURIER_NOT_ACTIVE)
                .build();

        return mapper.toDTO(courierRepository
                .save(courier));
    }

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

    public CourierDto updateCourierById(Long id, CourierDto courierRequest) {
        Courier courierResponse = mapper.toModel(getCourierById(id));
        mapper.updateCourierFromDto(courierRequest, courierResponse);
        courierResponse.setId(id);
        return mapper.toDTO(courierRepository
                .save(courierResponse));
    }

    public void updateCourierStatusById(Long id, StatusCourier statusCourier) {
        Courier courierResponse = mapper.toModel(getCourierById(id));
        courierResponse.setStatus(statusCourier);
        mapper.toDTO(courierRepository
                .save(courierResponse));
    }

}
