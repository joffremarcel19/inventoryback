package com.planificacion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;
import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Usuario;

public interface IDetallePoaService extends ICRUD<DetallePoa, Integer>{
	
	Page<DetallePoa> listarPageable(Pageable pageable);
	List<DetallePoa> listarDetallePoa(Integer idPoa);

}
