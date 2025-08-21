package com.planificacion.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100)
    private String name; // nombre
    @Column(name = "legal_representative") // Nombre del Representante Legal del proveedor (si aplica)
    private String legalRepresentative;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate; // fecha_creacion

    @Column(name = "status", nullable = false, length = 50)
    private String status; // estado (ej. "ACTIVE", "INACTIVE")

    @Column(name = "ruc", unique = true, length = 13) // RUC suele ser único y de 13 dígitos en Ecuador
    private String ruc; // ruc

    @Column(name = "business_name", nullable = false)
    private String businessName; // Razón Social (Legal Company Name)

    @Column(name = "company_name", nullable = true)
    private String companyName; // Nombre Comercial (Trading Name or Company Name)

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;
    
    @Column(name = "cell_number", length = 20)
    private String cellNumber; // celular

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "notes", nullable = true)
    private String notes;
}