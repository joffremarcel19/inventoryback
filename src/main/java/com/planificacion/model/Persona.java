package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPersona;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "nombres", length = 200)
    private String nombres;

    @Column(name = "apellidos", length = 200)
    private String apellidos;

    @Column(name = "profesion", length = 100)
    private String profesion;

    @Column(name = "telefono", length = 10)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "puesto", length = 100)
    private String puesto;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "adjetivo", length = 100)
    private String adjetivo;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_persona_unidad_administrativa"))
    private UnidadAdministrativa unidadAdministrativa;
}
