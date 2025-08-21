package com.planificacion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planificacion.model.Impresion;
import com.planificacion.model.Pei;
import com.planificacion.model.Persona;
import com.planificacion.model.PersonaImpresion;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;

public interface IImpresionService extends ICRUD<Impresion, Integer>{
	
	byte[] generarReporte(int idImpresion);
	
	List<PersonaImpresion> listarPersona(int idImpresion);

}
