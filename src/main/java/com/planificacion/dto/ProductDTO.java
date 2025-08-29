package com.planificacion.dto;

import com.planificacion.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String brand;
    private String model;
    private String color;
    private String description;
    private Boolean iva;
    private Boolean irbp;
    private Boolean ice;
    private String partNumber;
    private String idOlympo;
    private Integer performance;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.model = product.getModel();
        this.color = product.getColor();
        this.description = product.getDescription();
        this.iva = product.getIva();
        this.irbp = product.getIrbp();
        this.ice = product.getIce();
        this.partNumber = product.getPartNumber();
        this.idOlympo = product.getIdOlympo();
        this.performance = product.getPerformance();
    }
}