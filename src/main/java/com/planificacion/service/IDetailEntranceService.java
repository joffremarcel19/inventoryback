package com.planificacion.service;

import com.planificacion.model.DetailEntrance;
import java.util.List;
import java.util.Optional;

public interface IDetailEntranceService {
    DetailEntrance registerToConcert(Integer concertId, Integer entranceId) throws Exception;
    DetailEntrance registerToConcert(Integer concertId, Integer entranceId, String token, String status) throws Exception; // Método actualizado con status
    Optional<DetailEntrance> getDetailEntranceByToken(String token);
    // Nuevo método para cargar la relación concert
    Optional<DetailEntrance> getDetailEntranceByTokenWithConcert(String token);
    List<DetailEntrance> getRegistrationsByEntranceId(Integer entranceId);
    List<DetailEntrance> getRegistrationsByConcertId(Integer concertId);
    Optional<DetailEntrance> getDetailEntranceById(Integer id); // Agrega este método si no existe
    Optional<DetailEntrance> findByEntranceIdAndConcertId(Integer entranceId, Integer concertId); // ¡Asegúrate de tener esta línea!
    // ¡Agrega esta línea aquí!
    Optional<DetailEntrance> getDetailEntranceByTokenWithEntrance(String token);
    DetailEntrance markAsIngresado(Integer detailEntranceId) throws Exception;
}