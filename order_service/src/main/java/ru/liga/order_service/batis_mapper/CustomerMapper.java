package ru.liga.order_service.batis_mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.commons.status.StatusCourier;
import ru.liga.order_service.model.Courier;
import ru.liga.order_service.model.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {

    Customer findCustomerById(Long id);

    List<Customer> findAllCustomerByPhone(String phone);

}
