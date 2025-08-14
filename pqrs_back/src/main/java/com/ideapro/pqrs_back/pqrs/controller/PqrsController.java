package com.ideapro.pqrs_back.pqrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;
import com.ideapro.pqrs_back.pqrs.Security.PqrsDetailsImpl;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {
    @Autowired
    private PqrsService pqrsService;

    // NO NECESITA AUTORIZACION
    @PostMapping
    public Pqrs crearPqrs(@RequestBody Pqrs pqrs) {
        return pqrsService.crearPqrs(pqrs);
    }

    // SOLO ADMIN
    @GetMapping
    public ResponseEntity<?> listarPqrs(Authentication authentication) {
        PqrsDetailsImpl pqrsDetails = (PqrsDetailsImpl) authentication.getPrincipal();
        // Aquí deberías usar UserDetailsImpl si tienes usuarios con roles
        // y no PqrsDetailsImpl, pero se deja así por tu estructura actual
        if (!"ADMIN".equalsIgnoreCase(pqrsDetails.getUsername())) {
            return ResponseEntity.status(403).body("No tiene permisos para listar PQRS");
        }
        return ResponseEntity.ok(pqrsService.listarPqrs());
    }

    // SOLO ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPqrs(@PathVariable Long id, Authentication authentication) {
        PqrsDetailsImpl pqrsDetails = (PqrsDetailsImpl) authentication.getPrincipal();
        if (!"ADMIN".equalsIgnoreCase(pqrsDetails.getUsername())) {
            return ResponseEntity.status(403).body("No tiene permisos para ver este PQRS");
        }
        Pqrs pqrs = pqrsService.obtenerPqrs(id);
        if (pqrs == null) {
            return ResponseEntity.badRequest().body("El PQRS no existe");
        }
        return ResponseEntity.ok(pqrs);
    }

    // PUBLICO
    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public List<Pqrs> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        return pqrsService.buscarPorNumeroRadicado(numeroRadicado);
    }

    // SOLO ADMIN
    @PostMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPqrs(@PathVariable Long id, Authentication authentication) {
        PqrsDetailsImpl pqrsDetails = (PqrsDetailsImpl) authentication.getPrincipal();
        if (!"ADMIN".equalsIgnoreCase(pqrsDetails.getUsername())) {
            return ResponseEntity.status(403).body("No tiene permisos para eliminar PQRS");
        }
        Pqrs pqrs = pqrsService.obtenerPqrs(id);
        if (pqrs == null) {
            return ResponseEntity.badRequest().body("El PQRS que intenta eliminar no existe");
        }
        pqrsService.eliminarPqrs(id);
        return ResponseEntity.ok("PQRS eliminado correctamente.");
    }
}