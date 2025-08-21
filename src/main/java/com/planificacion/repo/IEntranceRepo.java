package com.planificacion.repo;

import com.planificacion.model.Entrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IEntranceRepo extends JpaRepository<Entrance, Integer> {
    // No necesitamos métodos personalizados por ahora para el registro básico.
    // JpaRepository proporciona los métodos CRUD básicos.
	List<Entrance> findByIdentity(String identity);
}
