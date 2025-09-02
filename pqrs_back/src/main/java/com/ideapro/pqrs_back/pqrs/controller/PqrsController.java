
package com.ideapro.pqrs_back.pqrs.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {

    private static final Logger logger = LoggerFactory.getLogger(PqrsController.class);

    @Autowired
    private PqrsService pqrsService;

    @PostMapping
    public ResponseEntity<Pqrs> crearPqrs(@Valid @RequestBody Pqrs pqrs) {
        // Validar que peticionario no sea null
        if (pqrs.getPeticionario() == null) {
            pqrs.setPeticionario(new Peticionario());
        }

        // Asegurarse de que los campos críticos no estén null
        Peticionario pet = pqrs.getPeticionario();
        if (pet.getApellidos() == null)
            pet.setApellidos("");
        if (pet.getNombres() == null)
            pet.setNombres("");
        if (pet.getTipoDocumento() == null)
            pet.setTipoDocumento("");
        if (pet.getNumeroDocumento() == null)
            pet.setNumeroDocumento("");
        if (pet.getTelefono() == null)
            pet.setTelefono("");
        if (pet.getEmail() == null)
            pet.setEmail("");

        Pqrs nuevaPqrs = pqrsService.crearPqrs(pqrs);
        logger.info("PQRS creada con ID {}", nuevaPqrs.getId());

        // Notificación a n8n solo si existe peticionario
        if (nuevaPqrs.getPeticionario() != null) {
            try {
                // Webhook temporalmente desactivado
                logger.info("Notificación enviada al webhook para PQRS ID {}", nuevaPqrs.getId());
            } catch (Exception e) {
                logger.error("Error al notificar al webhook para PQRS ID {}: {}", nuevaPqrs.getId(), e.getMessage());
            }
        }

        return ResponseEntity.status(201).body(nuevaPqrs);
    }

    // SOLO ADMIN
    @GetMapping
    public ResponseEntity<List<Pqrs>> listarPqrs() {
        List<Pqrs> lista = pqrsService.listarPqrs();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pqrs> obtenerPqrs(@PathVariable Long id) {
        return Optional.ofNullable(pqrsService.obtenerPqrs(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUBLICO
    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public ResponseEntity<List<Pqrs>> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        List<Pqrs> resultados = pqrsService.buscarPorNumeroRadicado(numeroRadicado);
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    // SOLO ADMIN
    @PostMapping("/eliminar/{id}")
    public void eliminarPqrs(@PathVariable Long id) {
        pqrsService.eliminarPqrs(id);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> obtenerTotalPqrs() {
        long total = pqrsService.contarPqrs();
        return ResponseEntity.ok(total);
    }

    // Nuevo endpoint para obtener el estado de una PQRS por su ID
    @GetMapping("/estado/{id}")
    public ResponseEntity<String> obtenerEstadoPqrs(@PathVariable Long id) {
        String estado = pqrsService.obtenerEstadoPqrs(id);
        return ResponseEntity.ok(estado);
    }

    // Nuevo endpoint para listar PQRS por estado
    @GetMapping("/porEstado/{estado}")
    public ResponseEntity<List<Pqrs>> listarPqrsPorEstado(@PathVariable String estado) {
        List<Pqrs> lista = pqrsService.listarPqrsPorEstado(estado);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //endpoint para traer el estado de una pqrs por numero de radicado
    // todos lo pueden ver
    @GetMapping("/estadoRadicado/{numeroRadicado}")
    public ResponseEntity<String> obtenerEstadoPorRadicado(@PathVariable String numeroRadicado){
        String estado = pqrsService.obtenerEstadoPorRadicado(numeroRadicado);
        return ResponseEntity.ok(estado);
    }
}