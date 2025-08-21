package com.planificacion.controller;


import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.MetaInstitucional;
import com.planificacion.model.ObjEstInstitucional;
import com.planificacion.model.ObjEstTer1;
import com.planificacion.model.ObjEstTer2;
import com.planificacion.model.Usuario;
import com.planificacion.model.Usuario;
import com.planificacion.service.ILoginService;
import com.planificacion.service.IMetaInstitucionalService;
import com.planificacion.service.IObjEstInstitucionalService;
import com.planificacion.service.IObjEstTer1Service;
import com.planificacion.service.IObjEstTer2Service;
import com.planificacion.service.IUsuarioService;
import com.planificacion.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	@GetMapping
	@PreAuthorize("@authServiceImpl.tieneAcceso('listar')")
	public ResponseEntity<List<Usuario>> listar() throws Exception {
		List<Usuario> lista = service.listar();
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario p) throws Exception {
		p.setPassword(bcrypt.encode(p.getPassword()));
		
		Usuario obj = service.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdUsuario()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Usuario> modificar(@Valid @RequestBody Usuario p) throws Exception {
		p.setPassword(bcrypt.encode(p.getPassword()));
		Usuario obj = service.modificar(p);
		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
