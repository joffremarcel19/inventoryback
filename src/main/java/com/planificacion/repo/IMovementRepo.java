package com.planificacion.repo;

import com.planificacion.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovementRepo extends JpaRepository<Movement, Integer> {
}
