package com.planificacion.service.impl;

import com.planificacion.model.Entrance;
import com.planificacion.repo.IEntranceRepo;
import com.planificacion.service.IEntranceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IEntranceServiceImpl implements IEntranceService {

    @Autowired
    private IEntranceRepo entranceRepo;

    @Override
    public Entrance registerEntrance(Entrance entrance) {
        return entranceRepo.save(entrance);
    }

    @Override
    public Optional<Entrance> getEntranceById(Integer id) {
        return entranceRepo.findById(id);
    }

    @Override
    public List<Entrance> getAllEntrances() {
        return entranceRepo.findAll();
    }

    @Override
    public Entrance updateEntrance(Integer id, Entrance entrance) {
        if (entranceRepo.existsById(id)) {
            entrance.setId(id);
            return entranceRepo.save(entrance);
        }
        return null; // Considerar lanzar excepción
    }

    @Override
    public void deleteEntrance(Integer id) {
        entranceRepo.deleteById(id);
    }
    @Override
    public List<Entrance> getEntrancesByIdentity(String identity) {
        return entranceRepo.findByIdentity(identity); // Implementación del nuevo método
    }
}