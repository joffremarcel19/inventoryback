package com.planificacion.service.impl;

import com.planificacion.model.Concert;
import com.planificacion.repo.IConcertRepo;
import com.planificacion.service.IConcertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IConcertServiceImpl implements IConcertService {

    @Autowired
    private IConcertRepo concertRepo;

    @Override
    public Concert createConcert(Concert concert) {
        return concertRepo.save(concert);
    }

    @Override
    public Optional<Concert> getConcertById(Integer id) {
        return concertRepo.findById(id);
    }

    @Override
    public List<Concert> getAllConcerts() {
        return concertRepo.findAll();
    }

    @Override
    public Concert updateConcert(Integer id, Concert concert) {
        if (concertRepo.existsById(id)) {
            concert.setId(id); // Aseguramos que el ID sea el correcto para la actualización
            return concertRepo.save(concert);
        }
        return null; // O podrías lanzar una excepción indicando que el concierto no existe
    }

    @Override
    public void deleteConcert(Integer id) {
        concertRepo.deleteById(id);
 
}
}