package ru.liga.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.liga.commons.mapper.PostgreSQLEnumType;
import ru.liga.commons.status.StatusRestaurantMenu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "restaurant_menu_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class RestaurantMenuItem implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_menu_item_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;

    private Double price;

    private String description;

    private String image;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private StatusRestaurantMenu status;

    @OneToOne(mappedBy = "restaurantMenuItem")
    @JsonIgnore
    private OrderItem orderItem;

}
