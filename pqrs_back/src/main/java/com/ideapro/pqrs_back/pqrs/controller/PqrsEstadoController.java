/**
 * Controlador para gestionar los estados de PQRS
 * pqrs/controller/PqrsEstadoController.java
 */
package com.ideapro.pqrs_back.pqrs.controller;

import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;
import com.ideapro.pqrs_back.pqrs.service.PqrsEstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class PqrsEstadoController {

    @Autowired
    private PqrsEstadoService estadoService;

    // Crear nuevo estado
    @PostMapping
    public ResponseEntity<PqrsEstado> crearEstado(@RequestBody PqrsEstado estado) {
        return ResponseEntity.ok(estadoService.guardar(estado));
    }

    // Listar todos los estados
    @GetMapping
    public ResponseEntity<List<PqrsEstado>> listarEstados() {
        List<PqrsEstado> lista = estadoService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // Buscar estado por ID
    @GetMapping("/{id}")
    public ResponseEntity<PqrsEstado> obtenerEstado(@PathVariable Long id) {
        return estadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar estado por nombre
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<PqrsEstado> buscarPorNombre(@PathVariable String nombre) {
        return estadoService.buscarPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar estado
    @PutMapping("/{id}")
    public ResponseEntity<PqrsEstado> actualizarEstado(@PathVariable Long id, @RequestBody PqrsEstado estado) {
        return estadoService.buscarPorId(id)
                .map(existente -> {
                    existente.setNombre(estado.getNombre());
                    existente.setDescripcion(estado.getDescripcion());
                    return ResponseEntity.ok(estadoService.guardar(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar estado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Long id) {
        estadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
