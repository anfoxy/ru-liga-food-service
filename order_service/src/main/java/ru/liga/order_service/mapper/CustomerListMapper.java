package ru.liga.order_service.mapper;

import org.mapstruct.Mapper;
import ru.liga.commons.dto.dto_model.CustomerDto;
import ru.liga.order_service.model.Customer;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface CustomerListMapper {

    List<Customer> toModelList(List<CustomerDto> dtos);

    List<CustomerDto> toDTOList(List<Customer> models);

}
