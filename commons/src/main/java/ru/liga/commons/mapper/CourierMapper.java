package ru.liga.commons.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.CourierDto;
import ru.liga.commons.model.Courier;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourierFromDto(CourierDto dto, @MappingTarget Courier entity);

    CourierDto toDTO(Courier model);

    Courier toModel(CourierDto dto);

}
