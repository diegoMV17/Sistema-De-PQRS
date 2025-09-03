/**
 * Servicio para gestionar los estados de las PQRS
 * pqrs/service/PqrsEstadoService.java
 */

package com.ideapro.pqrs_back.pqrs.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;
import com.ideapro.pqrs_back.pqrs.repository.PqrsEstadoRepository;

@Service
public class PqrsEstadoService {

    @Autowired
    private PqrsEstadoRepository estadoRepository;

    // Guardar un estado nuevo
    public PqrsEstado guardar(PqrsEstado estado) {
        return estadoRepository.save(estado);
    }

    // Buscar un estado por nombre
    public Optional<PqrsEstado> buscarPorNombre(String nombre) {
        return estadoRepository.findByNombre(nombre);
    }

    // Buscar un estado por ID
    public Optional<PqrsEstado> buscarPorId(Long id) {
        return estadoRepository.findById(id);
    }

    // Listar todos los estados
    public java.util.List<PqrsEstado> listar() {
        return estadoRepository.findAll();
    }

    // Actualizar un estado existente
    public PqrsEstado actualizar(PqrsEstado estado) {
        return estadoRepository.save(estado);
    }

    // Eliminar un estado por ID
    public void eliminar(Long id) {
        estadoRepository.deleteById(id);
    }}