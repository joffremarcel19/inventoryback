package com.planificacion.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.planificacion.model.Usuario;
import com.planificacion.repo.ILoginRepo;
import com.planificacion.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private ILoginRepo repo;
	
	@Override
	public Usuario verificarNombreUsuario(String usuario) {
		// TODO Auto-generated method stub
		return repo.verificarNombreUsuario(usuario);
	}

	@Override
	public void CambiarClave(String clave, String nombre) {
		repo.cambiarClave(bcrypt.encode(clave), nombre);		
	}

}
