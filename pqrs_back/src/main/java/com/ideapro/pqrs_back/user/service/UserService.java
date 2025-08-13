package com.ideapro.pqrs_back.user.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public User crearUser(User user) {
        return userRepository.save(user);
    }

    public List<User> listarUser() {
        return userRepository.findAll();
    }

    public User obtenerUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void eliminarUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> buscarEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Registro de usuario con validación y encriptación
    public User register(User user) {
        // Validar email existente
        if (!userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new RuntimeException("El email ya está registrado.");
        }
        // Validar credencial existente
        if (userRepository.findByCredencial(user.getCredencial()) != null) {
            throw new RuntimeException("La credencial ya está registrada.");
        }
        // Encriptar la contraseña antes de guardar
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        return userRepository.save(user);
    }

    // Login de usuario con comparación encriptada
    public User login(String credencial, String constrasenia) {
        User user = userRepository.findByCredencial(credencial);
        if (user != null && passwordEncoder.matches(constrasenia, user.getContrasena())) {
            return user;
        }
        return null;
    }
}
