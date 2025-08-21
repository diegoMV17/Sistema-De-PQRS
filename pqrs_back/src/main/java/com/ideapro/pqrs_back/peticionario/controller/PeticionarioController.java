package com.ideapro.pqrs_back.peticionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< recuperacion-backend
=======
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
>>>>>>> main
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.service.PeticionarioService;

@RestController
@RequestMapping("/api/peticionarios")
public class PeticionarioController {

    @Autowired
    private PeticionarioService peticionarioService;
<<<<<<< recuperacion-backend
    
    // Crear un nuevo Peticionario
=======

    // NO NECESITA AUTORIZACION
>>>>>>> main
    @PostMapping
    public Peticionario crearPeticionario(@RequestBody Peticionario peticionario) {
        return peticionarioService.crearPeticionario(peticionario);
    }

<<<<<<< recuperacion-backend
    // Listar todos los Peticionarios
    @GetMapping
    public List<Peticionario> listarPeticionarios() {
=======
    // SOLO ADMIN
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Peticionario> listarPeticionario() {
>>>>>>> main
        return peticionarioService.listarPeticionario();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Peticionario obtenerPeticionario(@PathVariable Long id) {
        return peticionarioService.obtenerPeticionario(id);
    }

<<<<<<< recuperacion-backend
    // Eliminar por ID
    @DeleteMapping("/{id}")
    public void eliminarPeticionario(@PathVariable Long id) {
        peticionarioService.eliminarPeticionario(id);
    }

    // Actualizar por ID
    @PutMapping("/{id}")
=======
    // SOLO ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public void eliminarPeticionario(@PathVariable Long id) {
        peticionarioService.eliminarPeticionario(id);
    }
    // SOLO ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/actualizar/{id}")
>>>>>>> main
    public Peticionario actualizarPeticionario(@PathVariable Long id, @RequestBody Peticionario peticionario) {
        return peticionarioService.actualizarPeticionario(id, peticionario);
    }

<<<<<<< recuperacion-backend
    // Buscar por documento
 


}
=======
    // PUBLICO
    @GetMapping("/buscarPorDocumento/{documento}")
    public List<Peticionario> buscarPorDocumento(@PathVariable String documento) {
        return peticionarioService.buscarPorDocumento(documento);
    }
}
>>>>>>> main
