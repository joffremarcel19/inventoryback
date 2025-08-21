package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.planificacion.model.Impresion;

public interface IImpresionRepo extends IGenericRepo<Impresion, Integer>  {
	@Query(value = "Select p.id_persona, p.nombres, p.apellidos, p.adjetivo, p.puesto, e.nombre_empresa from persona p inner join empresa e on p.id_empresa=e.id_empresa inner join impresion_persona ip on p.id_persona =ip.id_persona inner join impresion i on ip.id_impresion=i.id_impresion where i.id_impresion=:idimpresion", nativeQuery = true)
	List<Object[]> listarPersona(int idimpresion);
}
