package com.planificacion.service.impl;

import java.awt.SystemColor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

	public boolean tieneAcceso(String path) {
		System.out.print(path+"Pr");
		boolean rpta = false;

		String metodoRol = "";
		
		switch (path) {
		case "listar":
				metodoRol = "ADMIN,SECRETARIA";
			break;

		case "listarId":
				metodoRol = "ADMIN,USER,DBA,SECRETARIA";
			break;
		}
		
		String metodoRoles[] = metodoRol.split(",");
		
		Authentication usuarioLogueado = SecurityContextHolder.getContext().getAuthentication();
		
		//System.out.println(usuarioLogueado.getName());
		
		for(GrantedAuthority auth : usuarioLogueado.getAuthorities()) {
			String rolUser = auth.getAuthority();
		//	System.out.println(rolUser);
			
			for(String rolMet : metodoRoles) {
				if(rolUser.equalsIgnoreCase(rolMet)) {
					rpta = true;
				}
			}
		}
		return rpta;
	}
}
