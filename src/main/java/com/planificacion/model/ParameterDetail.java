package com.planificacion.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "parameter_detail")
public class ParameterDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name; // Ej: "Traspaso", "Prestamo", "Activo", "Desarrollo", "Compra"
    
    @Column(name = "value", nullable = false, length = 100)
    private String value; 

    @Column(name = "status", nullable = false, length = 20)
    private String status; // Ej: "act", "inact"

    @ManyToOne // Relación Muchos a Uno: Muchos ParameterDetails pertenecen a un Parameter
    @JsonBackReference
    @JoinColumn(name = "parameter_id", nullable = false, foreignKey = @ForeignKey(name = "FK_parameter_detail_parameter"))
    private Parameter parameter; // El Parameter al que pertenece este detalle

    public ParameterDetail() {
    }

    public ParameterDetail(String name, String status, Parameter parameter) {
        this.name = name;
        this.status = status;
        this.parameter = parameter;
    }
}