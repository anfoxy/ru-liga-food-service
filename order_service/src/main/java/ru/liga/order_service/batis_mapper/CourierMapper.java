package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.commons.status.StatusCourier;
import ru.liga.commons.model.Courier;

import java.util.List;

@Mapper
public interface CourierMapper {

    Courier findCourierById(Long id);

    List<Courier> findAllCourierByStatus(StatusCourier status);

}
