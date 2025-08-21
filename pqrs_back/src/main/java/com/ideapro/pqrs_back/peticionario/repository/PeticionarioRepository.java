package com.ideapro.pqrs_back.peticionario.repository;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeticionarioRepository extends JpaRepository<Peticionario, Long> {

    // Buscar un único peticionario por número de documento
    Optional<Peticionario> findByNumeroDocumento(String numeroDocumento);

    // Buscar por nombre o apellido (ejemplo flexible)
    List<Peticionario> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);

}
