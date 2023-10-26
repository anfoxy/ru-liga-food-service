package ru.liga.kitchen_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import ru.liga.kitchen_service.model.Order;
import ru.liga.kitchen_service.model.Restaurant;
import ru.liga.kitchen_service.model.RestaurantMenuItem;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-26T14:11:46+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public void updateRestaurantFromDto(Restaurant dto, Restaurant entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( entity.getOrders() != null ) {
            List<Order> list = dto.getOrders();
            if ( list != null ) {
                entity.getOrders().clear();
                entity.getOrders().addAll( list );
            }
        }
        else {
            List<Order> list = dto.getOrders();
            if ( list != null ) {
                entity.setOrders( new ArrayList<Order>( list ) );
            }
        }
        if ( entity.getRestaurantMenuItems() != null ) {
            List<RestaurantMenuItem> list1 = dto.getRestaurantMenuItems();
            if ( list1 != null ) {
                entity.getRestaurantMenuItems().clear();
                entity.getRestaurantMenuItems().addAll( list1 );
            }
        }
        else {
            List<RestaurantMenuItem> list1 = dto.getRestaurantMenuItems();
            if ( list1 != null ) {
                entity.setRestaurantMenuItems( new ArrayList<RestaurantMenuItem>( list1 ) );
            }
        }
    }
}
