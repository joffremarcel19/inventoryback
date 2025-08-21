package com.planificacion.controller;

import com.planificacion.model.DetailEntrance;
import com.planificacion.service.IDetailEntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/detail-entrances")
public class DetailEntranceController {

    @Autowired
    private IDetailEntranceService detailEntranceService;

    @PostMapping("/register")
    public ResponseEntity<DetailEntrance> registerToConcert(
            @RequestParam("concertId") Integer concertId,
            @RequestParam("entranceId") Integer entranceId) {
        try {
            DetailEntrance registration = detailEntranceService.registerToConcert(concertId, entranceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(registration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // O manejar la excepción de forma más específica
        }
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<DetailEntrance> getDetailEntranceByToken(@PathVariable String token) {
        Optional<DetailEntrance> detailEntrance = detailEntranceService.getDetailEntranceByTokenWithConcert(token);
        return detailEntrance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailEntrance> getDetailEntranceById(@PathVariable Integer id) {
        Optional<DetailEntrance> detailEntrance = detailEntranceService.getDetailEntranceById(id);
        return detailEntrance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Podrías agregar otros endpoints si es necesario
}