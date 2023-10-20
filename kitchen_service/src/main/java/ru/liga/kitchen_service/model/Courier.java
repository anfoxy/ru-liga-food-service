package ru.liga.kitchen_service.model;

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
@Table(name = "courier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courier_seq_gen")
    @SequenceGenerator(name = "courier_seq_gen", sequenceName = "courier_seq", allocationSize = 1)
    @Column(name = "courier_id")
    private Long id;

    private String phone;

    private String status;

    private String coordinates;

    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

}