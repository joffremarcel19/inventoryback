package com.planificacion.dto;

// Si usas Lombok, asegúrate de tenerlo importado y la anotación @Data
// import lombok.Data;
// @Data
public class UnidadPrincipalDTO {
    private Integer id;
    private String nombre; // <-- ¡Este campo es crucial!

    // Constructor que toma la entidad UnidadPrincipal
    // Asegúrate de importar com.planificacion.model.UnidadPrincipal
    public UnidadPrincipalDTO(com.planificacion.model.UnidadPrincipal unidadPrincipal) {
        if (unidadPrincipal != null) {
            this.id = unidadPrincipal.getId();
            this.nombre = unidadPrincipal.getNombre(); // <-- Asegúrate de mapear el nombre
        }
    }

    // --- Getters y Setters (si no usas Lombok) ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() { // <-- ¡Este getter es vital para la serialización JSON!
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}