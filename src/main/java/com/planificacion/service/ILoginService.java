package com.planificacion.service;

import com.planificacion.model.Usuario;

public interface ILoginService {
	Usuario verificarNombreUsuario(String usuario);
	void CambiarClave(String clave, String nombre);
}
