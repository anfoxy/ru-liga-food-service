package ru.liga.commons.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.CustomerDto;
import ru.liga.commons.model.Customer;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface CustomerListMapper {

    List<Customer> toModelList(List<CustomerDto> dtos);

    List<CustomerDto> toDTOList(List<Customer> models);

}
