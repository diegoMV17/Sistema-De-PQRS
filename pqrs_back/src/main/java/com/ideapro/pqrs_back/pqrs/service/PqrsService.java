/**
 * Servicio para gestionar las PQRS
 * pqrs/service/PqrsService.java
 */

package com.ideapro.pqrs_back.pqrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;
import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;

import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;
import com.ideapro.pqrs_back.pqrs.repository.PqrsEstadoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PqrsService {

    @Autowired
    private PqrsRepository pqrsRepository;

    @Autowired
    private PqrsEstadoRepository estadoRepository;

    @Autowired
    private PeticionarioRepository peticionarioRepository;



    // Crear nueva PQRS
    public Pqrs crearPqrs(Pqrs pqrs) {
        // Fecha automática
        pqrs.setFechaRegistro(LocalDateTime.now());

        // Genera radicado único
        pqrs.setNumeroRadicado(generarRadicado());

        // Estado inicial = "Pendiente"
        PqrsEstado estadoInicial = estadoRepository.findByNombre("Pendiente")
                .orElseThrow(() -> new EntityNotFoundException("Estado 'Pendiente' no existe en BD"));
        pqrs.setEstado(estadoInicial);



        // Verificar si el peticionario ya existe por número de documento o email
        Peticionario pet = pqrs.getPeticionario();
        if (pet != null) {
            Peticionario existente = peticionarioRepository
                    .findByNumeroDocumentoOrEmail(pet.getNumeroDocumento(), pet.getEmail())
                    .orElseGet(() -> peticionarioRepository.save(pet)); // guardar si no existe
            pqrs.setPeticionario(existente);
        }

        return pqrsRepository.save(pqrs);
    }

    // Generar número de radicado único
    private String generarRadicado() {
        String prefijo = "PQRS-NUM-";
        String consecutivo = String.format("%07d",
                ThreadLocalRandom.current().nextInt(1, 9_999_999));
        String anio = String.valueOf(LocalDate.now().getYear());
        return prefijo + consecutivo + "-" + anio;
    }

    // Listar todas las PQRS
    public List<Pqrs> listarPqrs() {
        return pqrsRepository.findAll();
    }

    // Obtener PQRS por ID
    public Pqrs obtenerPqrs(Long id) {
        return pqrsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PQRS no encontrada con id: " + id));
    }

    // Eliminar PQRS
    public void eliminarPqrs(Long id) {
        if (!pqrsRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. PQRS no encontrada con id: " + id);
        }
        pqrsRepository.deleteById(id);
    }

    // Buscar PQRS por número de radicado
    public List<Pqrs> buscarPorNumeroRadicado(String numeroRadicado) {
        return pqrsRepository.findByNumeroRadicado(numeroRadicado);
    }

    // Contar todas las PQRS
    public long contarPqrs() {
        return pqrsRepository.count();
    }

    // Obtener estado de una PQRS por su ID
    public PqrsEstado obtenerEstadoPqrs(Long id) {
        return pqrsRepository.findEstadoById(id)
                .orElseThrow(() -> new EntityNotFoundException("PQRS no encontrada con id: " + id));
    }

    // Listar PQRS por estado (por nombre del estado)
    public List<Pqrs> listarPqrsPorEstado(String nombreEstado) {
        return pqrsRepository.findByEstadoNombre(nombreEstado);
    }

    // Obtener estado de PQRS por número de radicado
    public PqrsEstado obtenerEstadoPorRadicado(String numeroRadicado) {
        return pqrsRepository.findEstadoByNumeroRadicado(numeroRadicado)
                .orElseThrow(() -> new EntityNotFoundException("PQRS no encontrada con número de radicado: " + numeroRadicado));
    }
}
