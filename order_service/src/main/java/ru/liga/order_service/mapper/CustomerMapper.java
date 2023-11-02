package ru.liga.order_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.liga.commons.dto.dto_model.CustomerDto;
import ru.liga.order_service.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(CustomerDto dto, @MappingTarget Customer entity);

    CustomerDto toDTO(Customer model);

    Customer toModel(CustomerDto dto);

}
