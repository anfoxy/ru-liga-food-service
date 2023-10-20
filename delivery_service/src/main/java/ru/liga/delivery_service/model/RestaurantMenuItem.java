package ru.liga.delivery_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "restaurant_menu_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenuItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_menu_item_seq_gen")
    @SequenceGenerator(name = "restaurant_menu_item_seq_gen", sequenceName = "restaurant_menu_item_seq", allocationSize = 1)
    @Column(name = "restaurant_menu_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;

    private Double price;

    private String description;

    private String image;

    private String status;

    @OneToOne(mappedBy = "restaurantMenuItem")
    @JsonIgnore
    private OrderItem orderItem;

    @Override
    public String toString() {
        return "RestaurantMenuItem{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
