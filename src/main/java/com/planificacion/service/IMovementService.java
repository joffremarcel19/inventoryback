package com.planificacion.service;

import com.planificacion.model.Movement;
import java.util.List;

public interface IMovementService {
    Movement register(Movement movement);
    List<Movement> listAll();
}