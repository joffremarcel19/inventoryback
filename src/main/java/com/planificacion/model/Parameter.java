package com.planificacion.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "parameter")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code; // Ej: "DELIVERY_TYPE", "HELPDESK_STATUS"

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name; // Ej: "Delivery Type", "Helpdesk Status"

    // Relación OneToMany: Un Parameter tiene muchos ParameterDetails
    // cascade = CascadeType.ALL significa que operaciones como persist o remove en Parameter
    // se propagarán a sus ParameterDetails.
    // orphanRemoval = true significa que si un ParameterDetail se desvincula de un Parameter, se elimina.
    @JsonBackReference
    @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParameterDetail> details;

    public Parameter() {
    }

    public Parameter(String code, String name) {
        this.code = code;
        this.name = name;
    }
}