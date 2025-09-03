/**
 * Controlador para los Logs de las PQRS
 * pqrs/controller/PqrsLogController.java
 */

package com.ideapro.pqrs_back.pqrs.controller;

import com.ideapro.pqrs_back.pqrs.model.PqrsLog;
import com.ideapro.pqrs_back.pqrs.service.PqrsLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pqrs/logs")
public class PqrsLogController {

    private final PqrsLogService logService;

    public PqrsLogController(PqrsLogService logService) {
        this.logService = logService;
    }

    // 🔹 Obtener todos los logs de una PQRS
    @GetMapping("/{pqrsId}")
    public ResponseEntity<List<PqrsLog>> obtenerLogsPorPqrs(@PathVariable Long pqrsId) {
        List<PqrsLog> logs = logService.listarPorPqrs(pqrsId);
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logs);
    }

    // 🔹 Crear un log manual (si se requiere)
    @PostMapping
    public ResponseEntity<PqrsLog> crearLog(@RequestBody PqrsLog log) {
        PqrsLog nuevoLog = logService.guardar(log);
        return ResponseEntity.status(201).body(nuevoLog);
    }
}
