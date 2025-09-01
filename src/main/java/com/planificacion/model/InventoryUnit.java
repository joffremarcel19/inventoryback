package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "InventoryUnit")
public class InventoryUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serial", unique = true)
    private String serial;

    @Column(name = "municipal_code")
    private String municipalCode;

    @ManyToOne
    @JoinColumn(name = "idProduct", nullable = false, foreignKey = @ForeignKey(name = "FK_inventario_producto"))
    private Product product;

    @Column(name = "stock")
    private int stock;
    @Column(name = "min_stock")
    private Integer minStock;

    @Column(name = "max_stock")
    private Integer maxStock;
    @ManyToOne
    @JoinColumn(name = "custodian_id", nullable = true, foreignKey = @ForeignKey(name = "FK_inventory_unit_custodian"))
    private Persona custodian;
    @Column(name = "status", nullable = true)
    private String status;
    
    @Column(name = "url_img", nullable = true)
    private String urlImg;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_unit", 
        joinColumns = @JoinColumn(name = "id_inventory_unit", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_unit", referencedColumnName = "idUniAdm")
    )
    private List<UnidadAdministrativa> unit;

}

