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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "type", nullable = true)
    private String type;

    // ✅ Campo único para el número de parte de suministros
    @Column(name = "part_number", nullable = true, unique = true)
    private String partNumber;
    
    @Column(name = "iva", nullable = false)
    private Boolean iva;
    @Column(name = "irbp", nullable = false)
    private Boolean irbp;
    @Column(name = "ice", nullable = false)
    private Boolean ice;
    
 // Nuevo campo para el rendimiento (numérico y no obligatorio)
    @Column(name = "performance", nullable = true)
    private Integer performance; 

    @Column(name = "description", nullable = true)
    private String description;
    
    @Column(name = "id_olympo", nullable = false, unique = true)
    private String idOlympo;


    public enum ProductType {
        EquipoInformatico,
        Suministro
    }

    public void setType(ProductType type) {
        this.type = type != null ? type.name() : null;
    }

    public ProductType getType() {
        return this.type != null ? ProductType.valueOf(this.type) : null;
    }
}