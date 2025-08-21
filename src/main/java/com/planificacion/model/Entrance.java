package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "entrance")
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "identity", nullable = false, unique = true)
    private String identity;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "names", nullable = false)
    private String names;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}