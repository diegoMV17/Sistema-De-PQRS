package com.ideapro.pqrs_back.peticionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.service.PeticionarioService;

@RestController
@RequestMapping("/api/peticionarios")
public class PeticionarioController {

    @Autowired
    private PeticionarioService peticionarioService;
    
    // Crear un nuevo Peticionario
    @PostMapping
    public Peticionario crearPeticionario(@RequestBody Peticionario peticionario) {
        return peticionarioService.crearPeticionario(peticionario);
    }

    // Listar todos los Peticionarios
    @GetMapping
    public List<Peticionario> listarPeticionarios() {
        return peticionarioService.listarPeticionario();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Peticionario obtenerPeticionario(@PathVariable Long id) {
        return peticionarioService.obtenerPeticionario(id);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public void eliminarPeticionario(@PathVariable Long id) {
        peticionarioService.eliminarPeticionario(id);
    }

    // Actualizar por ID
    @PutMapping("/{id}")
    public Peticionario actualizarPeticionario(@PathVariable Long id, @RequestBody Peticionario peticionario) {
        return peticionarioService.actualizarPeticionario(id, peticionario);
    }

    // Buscar por documento
 


}
