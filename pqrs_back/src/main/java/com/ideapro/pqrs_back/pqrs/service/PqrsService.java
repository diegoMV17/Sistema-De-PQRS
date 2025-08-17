package com.ideapro.pqrs_back.pqrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;
import com.ideapro.pqrs_back.pqrs.dto.CrearPqrsDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;

@Service
@RequiredArgsConstructor
public class PqrsService {
    @Autowired
    private final PqrsRepository pqrsRepository;
    private final PeticionarioRepository peticionarioRepository;

    @Transactional
    public Pqrs crearPqrsCompleto(CrearPqrsDTO dto) {
        Peticionario peticionario = new Peticionario();
        peticionario.setTipo(dto.getTipoPeticionario());
        peticionario.setNombre(dto.getNombre());
        peticionario.setApellido(dto.getApellido());
        peticionario.setTipoDocumento(dto.getTipoDocumento());
        peticionario.setDocumento(dto.getDocumento());
        peticionario.setTelefono(dto.getTelefono());
        peticionario.setEmail(dto.getEmail());
        peticionario.setDireccion(dto.getDireccion());

        // Guardar peticionario primero
        peticionario = peticionarioRepository.save(peticionario);

        // 2. Crear y guardar el PQRS
        Pqrs pqrs = new Pqrs();
        pqrs.setTipo(pqrs.getTipo());
        pqrs.setDescripcion(dto.getDescripcion());
        pqrs.setFechaRegistro(LocalDateTime.now());
        pqrs.setNumeroRadicado(generarRadicado());
        pqrs.setPeticionario(peticionario); // Asociar el peticionario

        return pqrsRepository.save(pqrs);
    }

    public String generarRadicado() {
        String prefijo = "PQRS-NUM-";
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String anio = String.valueOf(LocalDate.now().getYear());
        return prefijo + uuid + "-" + anio;
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
