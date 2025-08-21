package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Persona;

public interface IPersonaRepo extends IGenericRepo<Persona, Integer> {

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombres) LIKE LOWER(CONCAT('%', :nombres, '%')) AND LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))")
    List<Persona> buscarPorNombreYApellido(@Param("nombres") String nombres, @Param("apellidos") String apellidos);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))")
    List<Persona> buscarPorNombre(@Param("nombres") String nombres);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))")
    List<Persona> buscarPorApellido(@Param("apellidos") String apellidos);

    Persona findByCedula(@Param("cedula") String cedula);

    @Query("SELECT p FROM Persona p WHERE p.unidadAdministrativa.idUniAdm = :idUnidad")
    List<Persona> findByUnidadAdministrativa_IdUnidad(@Param("idUnidad") Integer idUnidad);
    
    // Nuevo método para buscar personas por Unidad Principal
    @Query("SELECT p FROM Persona p JOIN p.unidadAdministrativa ua WHERE ua.unidadPrincipal.id = :idUnidadPrincipal")
    List<Persona> findByUnidadAdministrativa_UnidadPrincipal_Id(@Param("idUnidadPrincipal") Integer idUnidadPrincipal);

    // Nuevo método para buscar cédulas que contengan una subcadena
    @Query("SELECT p FROM Persona p WHERE p.cedula LIKE %:cedula%")
    List<Persona> buscarPorRangoCedula(@Param("cedula") String cedula);
}