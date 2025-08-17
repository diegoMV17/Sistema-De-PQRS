package com.ideapro.pqrs_back.peticionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Peticionario> listarPeticionario() {
        return peticionarioService.listarPeticionario();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Peticionario obtenerPeticionario(@PathVariable Long id) {
        return peticionarioService.obtenerPeticionario(id);
    }

    // SOLO ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public void eliminarPeticionario(@PathVariable Long id) {
        peticionarioService.eliminarPeticionario(id);
    }
    // SOLO ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/actualizar/{id}")
    public Peticionario actualizarPeticionario(@PathVariable Long id, @RequestBody Peticionario peticionario) {
        return peticionarioService.actualizarPeticionario(id, peticionario);
    }

    // PUBLICO
    @GetMapping("/buscarPorDocumento/{documento}")
    public List<Peticionario> buscarPorDocumento(@PathVariable String documento) {
        return peticionarioService.buscarPorDocumento(documento);
    }
}