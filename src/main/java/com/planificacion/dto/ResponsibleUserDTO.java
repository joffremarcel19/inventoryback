package com.planificacion.dto;

import com.planificacion.model.Usuario; // ¡Importa tu entidad Usuario real!

// Si estás usando Lombok para getters/setters, puedes añadir @Data
// import lombok.Data;
// @Data
public class ResponsibleUserDTO {
    private Integer idUsuario;
    private String username; // Mapea a getUsername() de tu entidad Usuario (que es la columna 'nombre')
    private String nombres;
    private String apellidos;
    private UnidadAdministrativaDTO unidadAdministrativa; // Aquí usamos el DTO de la unidad administrativa

    /**
     * Constructor que mapea una entidad Usuario a un ResponsibleUserDTO.
     * @param usuario La entidad Usuario de la que se obtendrán los datos.
     */
    public ResponsibleUserDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.username = usuario.getUsername();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();

        // Mapea la unidad administrativa si existe
        if (usuario.getUnidadAdministrativa() != null) {
            this.unidadAdministrativa = new UnidadAdministrativaDTO(
                usuario.getUnidadAdministrativa().getIdUniAdm(),
                usuario.getUnidadAdministrativa().getNombreUni()
            );
        } else {
            this.unidadAdministrativa = null; // O puedes dejarlo vacío o con un DTO predeterminado si es necesario
        }
    }

    // --- Getters y Setters ---
    // Si no usas Lombok, asegúrate de añadir estos manualmente.
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }
}