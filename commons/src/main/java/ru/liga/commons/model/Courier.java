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
import ru.liga.commons.status.StatusCourier;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.UUID;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "courier")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Courier implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "courier_id")
    private UUID id;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private StatusCourier status;

    private String coordinates;

    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

}