package com.planificacion.dto;

import com.planificacion.model.Persona;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaDTO {
    private int idPersona;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String profesion;
    private String telefono;
    private String correo;
    private String puesto;
    private String direccion;
    private String ciudad;
    private String adjetivo;
    // We don't map the UnidadAdministrativa here to avoid circular references
    // If you need it, you would create UnidadAdministrativaDTO and map it here.

    public PersonaDTO(Persona persona) {
        this.idPersona = persona.getIdPersona();
        this.cedula = persona.getCedula();
        this.nombres = persona.getNombres();
        this.apellidos = persona.getApellidos();
        this.profesion = persona.getProfesion();
        this.telefono = persona.getTelefono();
        this.correo = persona.getCorreo();
        this.puesto = persona.getPuesto();
        this.direccion = persona.getDireccion();
        this.ciudad = persona.getCiudad();
        this.adjetivo = persona.getAdjetivo();
    }
}