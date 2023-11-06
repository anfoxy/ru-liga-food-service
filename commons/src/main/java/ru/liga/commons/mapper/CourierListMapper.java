package ru.liga.commons.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.model.Courier;

import java.util.List;

@Mapper(componentModel = "spring", uses = CourierMapper.class)
public interface CourierListMapper {

    List<Courier> toModelList(List<CourierDto> dtos);

    List<CourierDto> toDTOList(List<Courier> models);

}
