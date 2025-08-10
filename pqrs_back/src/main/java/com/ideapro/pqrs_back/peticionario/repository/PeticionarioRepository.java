package com.ideapro.pqrs_back.peticionario.repository;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeticionarioRepository extends JpaRepository<Peticionario, Long> {
       // buscar por documento
    List<Peticionario> findByDocumento(String documento);
}
