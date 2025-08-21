package com.planificacion.service;

import com.planificacion.model.Concert;
import java.util.List;
import java.util.Optional;

public interface IConcertService {

    Concert createConcert(Concert concert);

    Optional<Concert> getConcertById(Integer id);

    List<Concert> getAllConcerts();

    Concert updateConcert(Integer id, Concert concert);

    void deleteConcert(Integer id);

}
