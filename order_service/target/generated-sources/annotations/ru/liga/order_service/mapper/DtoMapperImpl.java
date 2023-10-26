package ru.liga.order_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import ru.liga.order_service.model.Order;
import ru.liga.order_service.model.OrderItem;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-26T14:12:36+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public void updateOrderFromDto(Order dto, Order entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCustomer() != null ) {
            entity.setCustomer( dto.getCustomer() );
        }
        if ( dto.getRestaurant() != null ) {
            entity.setRestaurant( dto.getRestaurant() );
        }
        if ( dto.getCourier() != null ) {
            entity.setCourier( dto.getCourier() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getTimestamp() != null ) {
            entity.setTimestamp( dto.getTimestamp() );
        }
        if ( entity.getOrderItems() != null ) {
            List<OrderItem> list = dto.getOrderItems();
            if ( list != null ) {
                entity.getOrderItems().clear();
                entity.getOrderItems().addAll( list );
            }
        }
        else {
            List<OrderItem> list = dto.getOrderItems();
            if ( list != null ) {
                entity.setOrderItems( new ArrayList<OrderItem>( list ) );
            }
        }
    }
}
