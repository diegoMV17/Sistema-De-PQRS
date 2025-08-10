package com.ideapro.pqrs_back.peticionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.service.PeticionarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/peticionarios")
public class PeticionarioController {

    @Autowired
    private PeticionarioService peticionarioService;
    
    @PostMapping
    public Peticionario crearPeticionario(@RequestBody Peticionario peticionario) {
        return peticionarioService.crearPeticionario(peticionario);
    }
    @GetMapping
    public List<Peticionario> listarPeticionario() {
        return peticionarioService.listarPeticionario();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Peticionario obtenerPeticionario(@PathVariable Long id) {
        return peticionarioService.obtenerPeticionario(id);
    }

    // Eliminar por ID
    @PostMapping("/eliminar/{id}")
    public void eliminarPeticionario(@PathVariable Long id) {
        peticionarioService.eliminarPeticionario(id);
    }
    // Actualizar por ID
    @PostMapping("/actualizar/{id}")
    public Peticionario actualizarPeticionario(@PathVariable Long id, @RequestBody Peticionario peticionario) {
        return peticionarioService.actualizarPeticionario(id, peticionario);
    }
    // Buscar por documento
    @GetMapping("/buscarPorDocumento/{documento}")
    public List<Peticionario> buscarPorDocumento(@PathVariable String documento) {
        return peticionarioService.buscarPorDocumento(documento);
    }
}
