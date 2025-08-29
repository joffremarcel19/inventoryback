package com.planificacion.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "submenu")
public class Submenu {

    @Id
    private int idSubmenu;
    @Column(name = "name", length = 150)
    private String name;
    @Column(name = "url", length = 100)
    private String url;
    @Column(name = "icono", length = 50)
    private String icon;

    // Relationship to the parent menu
    @JsonBackReference 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu_padre", referencedColumnName = "idMenu")
    private Menu parentMenu;

    // Relationship to the roles for permissions
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "submenu_rol", joinColumns = @JoinColumn(name = "id_submenu", referencedColumnName = "idSubmenu"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;
}