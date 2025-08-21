package com.ideapro.pqrs_back.pqrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PqrsService {

    @Autowired
    private PqrsRepository pqrsRepository;

    public Pqrs crearPqrs(Pqrs pqrs) {
        // Fecha automática
        pqrs.setFechaRegistro(LocalDateTime.now());

        // Genera radicado
        pqrs.setNumeroRadicado(generarRadicado());

        return pqrsRepository.save(pqrs);
    }

    private String generarRadicado() {
        String prefijo = "PQRS-NUM-";
        String consecutivo = String.format("%07d",
                ThreadLocalRandom.current().nextInt(1, 9_999_999)
        );
        String anio = String.valueOf(LocalDate.now().getYear());
        return prefijo + consecutivo + "-" + anio;
    }

    public List<Pqrs> listarPqrs() {
        return pqrsRepository.findAll();
    }

    public Pqrs obtenerPqrs(Long id) {
        return pqrsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PQRS no encontrada con id: " + id));
    }

    public void eliminarPqrs(Long id) {
        if (!pqrsRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. PQRS no encontrada con id: " + id);
        }
        pqrsRepository.deleteById(id);
    }

    public List<Pqrs> buscarPorNumeroRadicado(String numeroRadicado) {
        return pqrsRepository.findByNumeroRadicado(numeroRadicado);
    }
}
