package com.planificacion.repo;

import com.planificacion.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConcertRepo extends JpaRepository<Concert, Integer> {
   
}