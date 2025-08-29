package com.planificacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String brand;
    private String model;
    private String color;
    private String type;
    private String serial;
    private String code;
    private String description;
    @JsonProperty("id")
    private Integer idUnit;
    
    // ✅ NUEVOS CAMPOS AGREGADOS
    private Boolean iva;
    private Boolean irbp;
    private Boolean ice;
    private String partNumber;
    private Integer performance;
    private String idOlympo;
}