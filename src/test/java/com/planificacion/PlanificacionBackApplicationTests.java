package com.planificacion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;
import com.planificacion.repo.IUsuarioRepo;



@SpringBootTest
class PlanificacionBackApplicationTests {

	@Autowired
	private IUsuarioRepo repo;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Test
	void verificarClave() {
		Usuario us = new Usuario();
		us.setIdUsuario(1);
		us.setUsername("0703806307");
		us.setPassword(bcrypt.encode("123456"));				
		us.setEnabled(true);
		UnidadAdministrativa es= new UnidadAdministrativa();
		es.setIdUniAdm(1);
		us.setUnidadAdministrativa(es);
		Usuario retorno = repo.save(us);
		
		assertTrue(retorno.getPassword().equalsIgnoreCase(us.getPassword()));
	}
}
