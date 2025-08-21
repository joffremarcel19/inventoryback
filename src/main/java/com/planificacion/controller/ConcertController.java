package com.planificacion.controller;

import com.planificacion.model.Concert;
import com.planificacion.service.IConcertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    private IConcertService concertService;

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) {
        Concert createdConcert = concertService.createConcert(concert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdConcert.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdConcert);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Integer id) {
        Optional<Concert> concert = concertService.getConcertById(id);
        return concert.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        List<Concert> concerts = concertService.getAllConcerts();
        return ResponseEntity.ok(concerts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concert> updateConcert(@PathVariable Integer id, @RequestBody Concert concert) {
        Concert updatedConcert = concertService.updateConcert(id, concert);
        return updatedConcert != null ? ResponseEntity.ok(updatedConcert) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Integer id) {
        concertService.deleteConcert(id);
        return ResponseEntity.noContent().build();
    }
}