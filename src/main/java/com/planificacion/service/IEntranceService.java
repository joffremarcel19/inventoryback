package com.planificacion.service;

import com.planificacion.model.Entrance;
import java.util.List;
import java.util.Optional;

public interface IEntranceService {
    Entrance registerEntrance(Entrance entrance);
    Optional<Entrance> getEntranceById(Integer id);
    List<Entrance> getAllEntrances();
    Entrance updateEntrance(Integer id, Entrance entrance);
    void deleteEntrance(Integer id);
    List<Entrance> getEntrancesByIdentity(String identity);
}
