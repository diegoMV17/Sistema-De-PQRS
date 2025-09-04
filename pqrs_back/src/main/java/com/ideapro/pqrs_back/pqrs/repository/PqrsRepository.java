/**
 * Repositorio para las PQRS (Peticiones, Quejas, Reclamos y Sugerencias)
 * pqrs/repository/PqrsRepository.java
 */

package com.ideapro.pqrs_back.pqrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
    
    // Buscar PQRS por número de radicado
    List<Pqrs> findByNumeroRadicado(String numeroRadicado);

    List<Pqrs> findByPeticionario_NumeroDocumento(String numeroDocumento);

    // Contar todas las PQRS
    long count();

    // Traer el estado de una PQRS por ID
    @Query("SELECT p.estado FROM Pqrs p WHERE p.id = :id")
    Optional<PqrsEstado> findEstadoById(@Param("id") Long id);

    // Traer el estado de una PQRS por número de radicado
    @Query("SELECT p.estado FROM Pqrs p WHERE p.numeroRadicado = :numeroRadicado")
    Optional<PqrsEstado> findEstadoByNumeroRadicado(@Param("numeroRadicado") String numeroRadicado);

List<Pqrs> findByEstadoNombre(String nombreEstado);

}
