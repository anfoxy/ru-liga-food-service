package ru.liga.delivery_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusCourier;
import ru.liga.delivery_service.exception.CreationException;
import ru.liga.delivery_service.exception.ResourceNotFoundException;
import ru.liga.delivery_service.mapper.CourierListMapper;
import ru.liga.delivery_service.mapper.CourierMapper;
import ru.liga.delivery_service.model.Courier;
import ru.liga.delivery_service.repository.CourierRepository;
import ru.liga.delivery_service.service.rabbit.MessageSenderCourier;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper mapper;
    private final CourierListMapper listMapper;
    private final DistanceCalculator distanceCalculator;
    private final OrderToDeliveryConverter toDeliveryConverter;
    private final LocalityDeterminant localityDeterminant;
    private final MessageSenderCourier messageSender;

    public CourierDto getCourierById(Long id) {
        return mapper.toDTO(courierRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new));
    }

    public List<CourierDto> getCourierByStatusActive() {
        return listMapper.toDTOList(courierRepository
                        .findAllByStatus(StatusCourier.COURIER_ACTIVE));
    }

    public CourierDto courierCreate(CourierDto courierRequest) {
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

    public CourierDto courierUpdateById(Long id, CourierDto courierRequest) {
        Courier courierResponse = mapper.toModel(getCourierById(id));
        mapper.updateCourierFromDto(courierRequest, courierResponse);
        courierResponse.setId(id);
        return mapper.toDTO(courierRepository
                        .save(courierResponse));
    }

    public void courierUpdateStatusById(Long id, StatusCourier statusCourier) {
        Courier courierResponse = mapper.toModel(getCourierById(id));
        courierResponse.setStatus(statusCourier);
        mapper.toDTO(courierRepository
                        .save(courierResponse));
    }

    public void setCourierForDelivery(OrderDto orderDto, String location) {
        log.info("CourierService: search for the nearest courier in area " + location);
        CourierDto courierDto = getClosestCourier(orderDto.getRestaurant().getAddress(), location);

        if (courierDto != null) {
            log.info("CourierService: The nearest courier has been found: " + courierDto);
            messageSender.sendMessage(toDeliveryConverter.deliveryDtoCreateFromOrder(orderDto, courierDto));
            return;
        }
        log.info("CourierService: The nearest courier was not found");
        //доделать с ожиданием
    }



}
