package ru.liga.delivery_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.delivery_service.model.Courier;



@Mapper(componentModel = "spring")
public interface DtoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourierFromDto(Courier dto, @MappingTarget Courier entity);

}
