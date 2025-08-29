package com.planificacion.controller;

import com.planificacion.model.Submenu;
import com.planificacion.service.ISubmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/submenus")
public class SubmenuController {

    @Autowired
    private ISubmenuService submenuService;

    @GetMapping
    public ResponseEntity<List<Submenu>> listAll() throws Exception {
        List<Submenu> submenus = submenuService.listar();
        return new ResponseEntity<>(submenus, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Submenu> listById(@PathVariable("id") Integer id) throws Exception {
        Submenu submenu = submenuService.listarPorId(id);
        return new ResponseEntity<>(submenu, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Submenu> register(@RequestBody Submenu submenu) throws Exception {
        Submenu obj = submenuService.registrar(submenu);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSubmenu()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Submenu> modify(@RequestBody Submenu submenu) throws Exception {
        Submenu obj = submenuService.modificar(submenu);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        submenuService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Submenu>> getSubmenusByUser(@RequestParam("username") String username) {
        try {
            List<Submenu> submenus = submenuService.listarSubmenuPorUsuario(username);
            return ResponseEntity.ok(submenus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}