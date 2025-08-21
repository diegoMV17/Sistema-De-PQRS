/**
 * Repositorio para las PQRS (Peticiones, Quejas, Reclamos y Sugerencias)
 */

package com.ideapro.pqrs_back.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
    List<Pqrs> findByNumeroRadicado(String numeroRadicado);
}
