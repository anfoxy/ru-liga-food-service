package ru.liga.order_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.liga.commons.status.StatusOrders;
import ru.liga.order_service.mapper.PostgreSQLEnumType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Order implements Serializable {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private StatusOrders status;

    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

}
