package com.ideapro.pqrs_back.pqrs.controller;

import java.util.List;
import java.util.Map;

import com.ideapro.pqrs_back.pqrs.dto.CrearPqrsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;

@RestController
@RequestMapping("/api/pqrs")
@RequiredArgsConstructor
public class PqrsController {

    private final PqrsService pqrsService;

    // NO NECESITA AUTORIZACION
    @PostMapping("/")
    public ResponseEntity<?> crearPqrs(@RequestBody CrearPqrsDTO dto) {
        try {
            Pqrs pqrs = pqrsService.crearPqrsCompleto(dto);
            return ResponseEntity.ok(Map.of(
                    "message", "PQRS creado exitosamente",
                    "numeroRadicado", pqrs.getNumeroRadicado(),
                    "id", pqrs.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // SOLO ADMIN
    @GetMapping
    public List<Pqrs> listarPqrs() {
        return pqrsService.listarPqrs();
    }

    // SOLO ADMIN
    public Pqrs obtenerPqrs(@PathVariable Long id) {
        return pqrsService.obtenerPqrs(id);
    }

    // PUBLICO
    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public List<Pqrs> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        return pqrsService.buscarPorNumeroRadicado(numeroRadicado);
    }

    // SOLO ADMIN
    @PostMapping("/eliminar/{id}")
    public void eliminarPqrs(@PathVariable Long id) {
        pqrsService.eliminarPqrs(id);
    }
}