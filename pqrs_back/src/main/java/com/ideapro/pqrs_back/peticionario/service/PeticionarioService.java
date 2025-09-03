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

    // Crear un nuevo Peticionario
    public Peticionario crearPeticionario(Peticionario peticionario) {
        return peticionarioRepository.save(peticionario);
    }

    // Listar todos
    public List<Peticionario> listarPeticionario() {
        return peticionarioRepository.findAll();
    }

    // Buscar por ID
    public Peticionario obtenerPeticionario(Long id) {
        return peticionarioRepository.findById(id).orElse(null);
    }

    // Eliminar por ID
    public void eliminarPeticionario(Long id) {
        peticionarioRepository.deleteById(id);
    }

    // Actualizar por ID
    public Peticionario actualizarPeticionario(Long id, Peticionario peticionario) {
        if (peticionarioRepository.existsById(id)) {
            peticionario.setId(id);
            return peticionarioRepository.save(peticionario);
        }
        return null; // O lanzar excepción
    }

    // Buscar por número de documento
    public Peticionario buscarPorDocumento(String numeroDocumento) {
        return peticionarioRepository.findByNumeroDocumento(numeroDocumento)
                                     .orElse(null);
    }

    // Buscar por nombre o apellido
    public List<Peticionario> buscarPorNombreOApellido(String criterio) {
        return peticionarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(criterio, criterio);
    }

    public Peticionario buscarPorNumeroDocumentoOEmail(String numeroDocumento, String email) {
       
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorNumeroDocumentoOEmail'");
    }

    public List<Peticionario> buscarPorNombresOApellidos(String nombres, String apellidos) {
       
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorNombresOApellidos'");
    }
}
