package com.ideapro.pqrs_back.pqrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;

@Service
public class PqrsService {
    @Autowired
    private PqrsRepository pqrsRepository;

    public Pqrs crearPqrs(Pqrs pqrs) {
        pqrs.setFechaRegistro(LocalDateTime.now());
        pqrs.setNumeroRadicado(generarRadicado()); // Genera radicado con la función
        return pqrsRepository.save(pqrs);
    }

    public String generarRadicado() {
        String prefijo = "PQRS-NUM-";
        String consecutivo = String.format("%07d", new Random().nextInt(9999999));
        String anio = String.valueOf(LocalDate.now().getYear());
        return prefijo + consecutivo + "-" + anio;
    }

    public List<Pqrs> listarPqrs() {
        return pqrsRepository.findAll();
    }

    public Pqrs obtenerPqrs(Long id) {
        return pqrsRepository.findById(id).orElse(null);
    }

    public void eliminarPqrs(Long id) {
        pqrsRepository.deleteById(id);
    }

    public List<Pqrs> buscarPorNumeroRadicado(String numeroRadicado) {
        return pqrsRepository.findByNumeroRadicado(numeroRadicado);
    }

}
