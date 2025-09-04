/**
 * Controlador para las PQRS (Peticiones, Quejas, Reclamos y Sugerencias)
 * pqrs/controller/PqrsController.java
 */
package com.ideapro.pqrs_back.pqrs.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {

    private static final Logger logger = LoggerFactory.getLogger(PqrsController.class);

    @Autowired
    private PqrsService pqrsService;

    // Crear PQRS
    @PostMapping
    public ResponseEntity<Pqrs> crearPqrs(@Valid @RequestBody Pqrs pqrs) {
        if (pqrs.getPeticionario() == null) {
            pqrs.setPeticionario(new Peticionario());
        }

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

        return ResponseEntity.status(201).body(nuevaPqrs);
    }

    // Listar todas las PQRS
    @GetMapping
    public ResponseEntity<List<Pqrs>> listarPqrs() {
        List<Pqrs> lista = pqrsService.listarPqrs();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // Obtener una PQRS por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pqrs> obtenerPqrs(@PathVariable Long id) {
        return Optional.ofNullable(pqrsService.obtenerPqrs(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar PQRS por número de radicado
    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public ResponseEntity<List<Pqrs>> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        List<Pqrs> resultados = pqrsService.buscarPorNumeroRadicado(numeroRadicado);
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscarPorDocumento/{numeroDocumento}")
    public ResponseEntity<List<Pqrs>> buscarPorDocumento(@PathVariable String numeroDocumento) {
        List<Pqrs> resultados = pqrsService.buscarPorNumeroDocumento(numeroDocumento);

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    

    // Eliminar PQRS
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPqrs(@PathVariable Long id) {
        pqrsService.eliminarPqrs(id);
        return ResponseEntity.noContent().build();
    }

    // Contar total de PQRS
    @GetMapping("/total")
    public ResponseEntity<Long> obtenerTotalPqrs() {
        long total = pqrsService.contarPqrs();
        return ResponseEntity.ok(total);
    }

    // Obtener estado de una PQRS por su ID
    @GetMapping("/estado/{id}")
    public ResponseEntity<String> obtenerEstadoPqrs(@PathVariable Long id) {
        String estado = pqrsService.obtenerEstadoPqrs(id).getNombre();
        return ResponseEntity.ok(estado);
    }

    // Obtener estado de una PQRS por número de radicado
    @GetMapping("/estado/radicado/{numeroRadicado}")
    public ResponseEntity<String> obtenerEstadoPorRadicado(@PathVariable String numeroRadicado) {
        String estado = pqrsService.obtenerEstadoPorRadicado(numeroRadicado).getNombre();
        return ResponseEntity.ok(estado);
    }

    // Listar PQRS por estado
    @GetMapping("/porEstado/{estado}")
    public ResponseEntity<List<Pqrs>> listarPqrsPorEstado(@PathVariable String estado) {
        List<Pqrs> lista = pqrsService.listarPqrsPorEstado(estado);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

}
