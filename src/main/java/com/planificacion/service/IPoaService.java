package com.planificacion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Poa;
import com.planificacion.model.Usuario;

public interface IPoaService extends ICRUD<Poa, Integer>{
	
	Page<Poa> listarPageable(Pageable pageable);
	List<Poa> listarPoaAnio(Integer anio, Integer idUni);
	List<Poa> listarPoaPorAnio(Integer anio);

}
