package com.planificacion.dto;

import com.planificacion.model.UnidadAdministrativa;

// Puedes añadir Lombok si lo usas en tu proyecto
// import lombok.Data;
// @Data
public class UnidadAdministrativaDTO {
    private Integer idUniAdm;
    private String nombreUni;
    private UnidadPrincipalDTO unidadPrincipal; // <-- ¡NUEVO CAMPO PARA LA UNIDAD PRINCIPAL!

    public UnidadAdministrativaDTO(UnidadAdministrativa unidadAdministrativa) {
        this.idUniAdm = unidadAdministrativa.getIdUniAdm();
        this.nombreUni = unidadAdministrativa.getNombreUni();
        // Mapea la UnidadPrincipal solo si no es nula en la entidad
        if (unidadAdministrativa.getUnidadPrincipal() != null) {
            this.unidadPrincipal = new UnidadPrincipalDTO(unidadAdministrativa.getUnidadPrincipal());
        }
    }

    public UnidadAdministrativaDTO(Integer idUniAdm, String nombreUni) {
        this.idUniAdm = idUniAdm;
        this.nombreUni = nombreUni;
        this.unidadPrincipal = null; // Inicializa a null si no se proporciona
    }

    // --- Getters y Setters ---
    public Integer getIdUniAdm() {
        return idUniAdm;
    }

    public void setIdUniAdm(Integer idUniAdm) {
        this.idUniAdm = idUniAdm;
    }

    public String getNombreUni() {
        return nombreUni;
    }

    public void setNombreUni(String nombreUni) {
        this.nombreUni = nombreUni;
    }

    // ¡Nuevos Getters y Setters para unidadPrincipal!
    public UnidadPrincipalDTO getUnidadPrincipal() {
        return unidadPrincipal;
    }

    public void setUnidadPrincipal(UnidadPrincipalDTO unidadPrincipal) {
        this.unidadPrincipal = unidadPrincipal;
    }
}