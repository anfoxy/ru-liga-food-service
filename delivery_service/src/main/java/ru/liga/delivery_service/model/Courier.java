package ru.liga.delivery_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.liga.commons.status.StatusCourier;
import ru.liga.delivery_service.mapper.PostgreSQLEnumType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "courier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Courier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courier_seq_gen")
    @SequenceGenerator(name = "courier_seq_gen", sequenceName = "courier_seq", allocationSize = 1)
    @Column(name = "courier_id")
    private Long id;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private StatusCourier status;

    private String coordinates;

    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}