package com.planificacion.service.impl;

import com.planificacion.model.Concert;
import com.planificacion.model.DetailEntrance;
import com.planificacion.model.Entrance;
import com.planificacion.repo.IConcertRepo;
import com.planificacion.repo.IDetailEntranceRepo;
import com.planificacion.repo.IEntranceRepo;
import com.planificacion.service.IDetailEntranceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ¡Asegúrate de tener esta dependencia!

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional // Asegura que los métodos en este servicio se ejecuten dentro de una transacción
public class IDetailEntranceServiceImpl implements IDetailEntranceService {

    @Autowired
    private IDetailEntranceRepo detailEntranceRepo;

    @Autowired
    private IConcertRepo concertRepo;

    @Autowired
    private IEntranceRepo entranceRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DetailEntrance registerToConcert(Integer concertId, Integer entranceId) throws Exception {
        Optional<Concert> concertOptional = concertRepo.findById(concertId);
        Optional<Entrance> entranceOptional = entranceRepo.findById(entranceId);

        if (!concertOptional.isPresent()) {
            throw new Exception("Concert not found with ID: " + concertId);
        }
        if (!entranceOptional.isPresent()) {
            throw new Exception("Entrance not found with ID: " + entranceId);
        }

        Concert concert = entityManager.merge(concertOptional.get());
        Entrance entrance = entityManager.merge(entranceOptional.get());

        // Verificar si ya está registrado
        Optional<DetailEntrance> existingRegistration = detailEntranceRepo.findByConcert_IdAndEntrance_Id(concertId, entranceId);
        if (existingRegistration.isPresent()) {
            throw new Exception("User is already registered for this concert.");
        }

        DetailEntrance detailEntrance = new DetailEntrance();
        detailEntrance.setConcert(concert);
        detailEntrance.setEntrance(entrance);
        detailEntrance.setStatus("Registered");
        detailEntrance.setToken(UUID.randomUUID().toString()); // Generar token único

        return detailEntranceRepo.save(detailEntrance);
    }


    @Override
    public DetailEntrance registerToConcert(Integer concertId, Integer entranceId, String token, String status) throws Exception {
        Optional<Concert> concertOptional = concertRepo.findById(concertId);
        Optional<Entrance> entranceOptional = entranceRepo.findById(entranceId);

        System.out.println("Buscando Concierto con ID: " + concertId + ", Encontrado: " + concertOptional.isPresent());
        System.out.println("Buscando Entrada con ID: " + entranceId + ", Encontrado: " + entranceOptional.isPresent());

        if (!concertOptional.isPresent()) {
            throw new Exception("Concert not found with ID: " + concertId);
        }
        if (!entranceOptional.isPresent()) {
            throw new Exception("Entrance not found with ID: " + entranceId);
        }

        Concert concert = entityManager.merge(concertOptional.get()); // ¡Usando entityManager.merge()!
        Entrance entrance = entityManager.merge(entranceOptional.get()); // ¡Usando entityManager.merge()!

        System.out.println("Concierto Obtenido (merged): ID=" + concert.getId() + ", Nombre=" + concert.getName());
        System.out.println("Entrada Obtenida (merged): ID=" + entrance.getId() + ", Identidad=" + entrance.getIdentity());

        // Verificar si ya está registrado
        Optional<DetailEntrance> existingRegistration = detailEntranceRepo.findByConcert_IdAndEntrance_Id(concertId, entranceId);
        if (existingRegistration.isPresent()) {
            throw new Exception("User is already registered for this concert.");
        }

        DetailEntrance detailEntrance = new DetailEntrance();
        detailEntrance.setConcert(concert);
        detailEntrance.setEntrance(entrance);
        detailEntrance.setStatus(status);
        detailEntrance.setToken(token);

        System.out.println("A punto de guardar DetailEntrance:");
        System.out.println("  Concert: " + detailEntrance.getConcert());
        System.out.println("  Entrance: " + detailEntrance.getEntrance());
        System.out.println("  Status: " + detailEntrance.getStatus());
        System.out.println("  Token: " + detailEntrance.getToken());

        return detailEntranceRepo.save(detailEntrance);
    }

    @Override
    public Optional<DetailEntrance> getDetailEntranceByToken(String token) {
        return detailEntranceRepo.findByToken(token);
    }

    @Override
    public Optional<DetailEntrance> getDetailEntranceByTokenWithConcert(String token) {
        return detailEntranceRepo.findByTokenWithConcert(token);
    }

    @Override
    public List<DetailEntrance> getRegistrationsByEntranceId(Integer entranceId) {
        return detailEntranceRepo.findByEntrance_Id(entranceId);
    }

    @Override
    public List<DetailEntrance> getRegistrationsByConcertId(Integer concertId) {
        return detailEntranceRepo.findByConcert_Id(concertId);
    }

    @Override
    public Optional<DetailEntrance> getDetailEntranceById(Integer id) {
        return detailEntranceRepo.findById(id);
    }
    @Override
    public Optional<DetailEntrance> findByEntranceIdAndConcertId(Integer entranceId, Integer concertId) {
        return detailEntranceRepo.findByEntrance_IdAndConcert_Id(entranceId, concertId); // ¡Implementación del método!
    }
    @Override
    public Optional<DetailEntrance> getDetailEntranceByTokenWithEntrance(String token) {
        return detailEntranceRepo.findByTokenWithEntrance(token);
    }
    @Override
    public DetailEntrance markAsIngresado(Integer detailEntranceId) throws Exception {
        Optional<DetailEntrance> detailEntranceOptional = detailEntranceRepo.findById(detailEntranceId);
        if (!detailEntranceOptional.isPresent()) {
            throw new Exception("Registro de entrada con ID " + detailEntranceId + " no encontrado.");
        }

        DetailEntrance detailEntrance = detailEntranceOptional.get();
        if (detailEntrance.getStatus().equalsIgnoreCase("Ingresado")) {
            throw new Exception("Usted ya ingresó al concierto.");
        }

        detailEntrance.setStatus("Ingresado");
        return detailEntranceRepo.save(detailEntrance);
    }
}