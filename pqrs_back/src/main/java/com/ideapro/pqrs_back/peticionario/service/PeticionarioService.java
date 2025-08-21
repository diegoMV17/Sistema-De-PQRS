package com.ideapro.pqrs_back.peticionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;

@Service
public class PeticionarioService {

    @Autowired
    private PeticionarioRepository peticionarioRepository;

    public Peticionario crearPeticionario(Peticionario peticionario) {
        return peticionarioRepository.save(peticionario);
    }

    public List<Peticionario> listarPeticionario() {
        return peticionarioRepository.findAll();
    }

    public Peticionario obtenerPeticionario(Long id) {
        return peticionarioRepository.findById(id).orElse(null);
    }
    public void eliminarPeticionario(Long id) {
        peticionarioRepository.deleteById(id);
    }
    public Peticionario actualizarPeticionario(Long id, Peticionario peticionario) {
        if (peticionarioRepository.existsById(id)) {
            peticionario.setId(id);
            return peticionarioRepository.save(peticionario);
        }
        return null; // O lanzar una excepción si no se encuentra el ID
    }

}
