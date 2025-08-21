package com.planificacion.repo;

import com.planificacion.model.UnidadAdministrativa;
import java.util.List;

public interface IUnidadAdministrativaRepo extends IGenericRepo<UnidadAdministrativa, Integer>  {
	 List<UnidadAdministrativa> findByUnidadPrincipal_Id(Integer idUnidadPrincipal);
}
