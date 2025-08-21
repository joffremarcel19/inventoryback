package com.planificacion.service;

import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;
import java.util.List;

public interface IUnidadAdministrativaService extends ICRUD<UnidadAdministrativa, Integer>{
	
	 List<UnidadAdministrativa> listarPorUnidadPrincipal(Integer idUnidadPrincipal) throws Exception;

}
