package com.ideapro.pqrs_back.peticionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.service.PeticionarioService;

@RestController
@RequestMapping("/api/peticionarios")
public class PeticionarioController {

    @Autowired
    private PeticionarioService peticionarioService;

    // NO NECESITA AUTORIZACION
    @PostMapping
    public Peticionario crearPeticionario(@RequestBody Peticionario peticionario) {
        return peticionarioService.crearPeticionario(peticionario);
    }

    // SOLO ADMIN
    @GetMapping
    public ResponseEntity<?> listarPeticionario(Authentication authentication) {
        if (!esAdmin(authentication)) {
            return ResponseEntity.status(403).body("No tiene permisos para listar peticionarios");
        }
        return ResponseEntity.ok(peticionarioService.listarPeticionario());
    }

    // SOLO ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPeticionario(@PathVariable Long id, Authentication authentication) {
        if (!esAdmin(authentication)) {
            return ResponseEntity.status(403).body("No tiene permisos para ver este peticionario");
        }
        Peticionario peticionario = peticionarioService.obtenerPeticionario(id);
        if (peticionario == null) {
            return ResponseEntity.badRequest().body("El peticionario no existe");
        }
        return ResponseEntity.ok(peticionario);
    }

    // SOLO ADMIN
    @PostMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPeticionario(@PathVariable Long id, Authentication authentication) {
        if (!esAdmin(authentication)) {
            return ResponseEntity.status(403).body("No tiene permisos para eliminar peticionarios");
        }
        Peticionario peticionario = peticionarioService.obtenerPeticionario(id);
        if (peticionario == null) {
            return ResponseEntity.badRequest().body("El peticionario que intenta eliminar no existe");
        }
        peticionarioService.eliminarPeticionario(id);
        return ResponseEntity.ok("Peticionario eliminado correctamente.");
    }

    // SOLO ADMIN
    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPeticionario(@PathVariable Long id, @RequestBody Peticionario peticionario, Authentication authentication) {
        if (!esAdmin(authentication)) {
            return ResponseEntity.status(403).body("No tiene permisos para actualizar peticionarios");
        }
        Peticionario actualizado = peticionarioService.actualizarPeticionario(id, peticionario);
        if (actualizado == null) {
            return ResponseEntity.badRequest().body("El peticionario que intenta actualizar no existe");
        }
        return ResponseEntity.ok(actualizado);
    }

    // PUBLICO
    @GetMapping("/buscarPorDocumento/{documento}")
    public List<Peticionario> buscarPorDocumento(@PathVariable String documento) {
        return peticionarioService.buscarPorDocumento(documento);
    }

    // Método auxiliar para verificar si el usuario es admin
    private boolean esAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            org.springframework.security.core.userdetails.UserDetails userDetails =
                    (org.springframework.security.core.userdetails.UserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }
}