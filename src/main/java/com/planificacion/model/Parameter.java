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
    private String code; 

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name; 

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