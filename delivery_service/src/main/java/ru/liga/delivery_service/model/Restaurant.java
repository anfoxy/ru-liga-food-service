package ru.liga.delivery_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq_gen")
    @SequenceGenerator(name = "restaurant_seq_gen", sequenceName = "restaurant_seq", allocationSize = 1)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name")
    private String name;

    private String address;

    private String status;

   @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RestaurantMenuItem> restaurantMenuItems;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
