/**
 * Repositorio para las PQRS (Peticiones, Quejas, Reclamos y Sugerencias)
 */

package com.ideapro.pqrs_back.pqrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
    List<Pqrs> findByNumeroRadicado(String numeroRadicado);

    long count();
    // traer el estado de una pqrs por id
    @Query("SELECT p.estado FROM Pqrs p WHERE p.id = :id")
    Optional<String> findEstadoById(@Param("id") Long id);

    // traer el estado de una pqrs por numero de radicado
    @Query("SELECT p.estado FROM Pqrs p WHERE p.numeroRadicado = :numeroRadicado")
    Optional<String> findEstadoByNumeroRadicado(@Param("numeroRadicado") String numeroRadicado);
    // traer las pqrs por estado
    List<Pqrs> findByEstado(String estado);
}