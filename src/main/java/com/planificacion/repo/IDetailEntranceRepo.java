package com.planificacion.repo;

import com.planificacion.model.DetailEntrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IDetailEntranceRepo extends JpaRepository<DetailEntrance, Integer> {

    // Método para verificar si una persona ya está inscrita en un concierto
    Optional<DetailEntrance> findByConcert_IdAndEntrance_Id(Integer concertId, Integer entranceId);

    // Método para encontrar inscripciones por token (para generar el QR)
    Optional<DetailEntrance> findByToken(String token);

    // Método para listar todas las inscripciones de una persona
    List<DetailEntrance> findByEntrance_Id(Integer entranceId);

    // Método para listar todas las inscripciones a un concierto
    List<DetailEntrance> findByConcert_Id(Integer concertId);

    // Nueva consulta para cargar la relación concert de forma eager
    @Query("SELECT de FROM DetailEntrance de JOIN FETCH de.concert WHERE de.token = :token")
    Optional<DetailEntrance> findByTokenWithConcert(@Param("token") String token);
    Optional<DetailEntrance> findByEntrance_IdAndConcert_Id(Integer entranceId, Integer concertId); // ¡Asegúrate de tener este también!
    @Query("SELECT de FROM DetailEntrance de JOIN FETCH de.entrance WHERE de.token = :token")
    Optional<DetailEntrance> findByTokenWithEntrance(@Param("token") String token);
}