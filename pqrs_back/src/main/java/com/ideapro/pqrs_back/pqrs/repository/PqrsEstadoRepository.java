/**
 * Repositorio para la entidad PqrsEstado
 * pqrs/repository/PqrsEstadoRepository.java
 */
package com.ideapro.pqrs_back.pqrs.repository;

import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PqrsEstadoRepository extends JpaRepository<PqrsEstado, Long> {
    // Buscar un estado por su nombre
    Optional<PqrsEstado> findByNombre(String nombre);
}
