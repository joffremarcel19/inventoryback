package com.planificacion.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Menu;
import com.planificacion.service.IMenuService;


@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private IMenuService service;
	
	@GetMapping
	public ResponseEntity<List<Menu>> listar() throws Exception{
		List<Menu> menus = new ArrayList<>();
		menus = service.listar();
		return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<List<Menu>> listar(@RequestBody String nombre) throws Exception{
		List<Menu> menus = new ArrayList<>();
		menus = service.listarMenuPorUsuario(nombre);		
		return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Menu> registrar(@RequestBody Menu c) throws Exception {
		
		Menu obj = service.registrar(c);
		
		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMenu}").buildAndExpand(obj.getIdMenu())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	

	// modificar
	@PutMapping
	public ResponseEntity<Menu> modificar(@RequestBody Menu c) throws Exception {
		
		Menu obj = service.modificar(c);
		return new ResponseEntity<Menu>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/{idMenu}")
	public ResponseEntity<Menu> listarPorId(@PathVariable("idMenu") Integer idMenu) throws Exception {
		Menu obj = service.listarPorId(idMenu);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + idMenu);
		}
		return new ResponseEntity<Menu>(obj, HttpStatus.OK);
	}

}