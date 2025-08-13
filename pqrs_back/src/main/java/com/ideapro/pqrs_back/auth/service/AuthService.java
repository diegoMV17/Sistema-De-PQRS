package com.ideapro.pqrs_back.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.auth.security.JwtUtil;
import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> login(String email, String contrasena) {
        var users = userRepository.findByEmail(email);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado");
        }
        var user = users.get(0);

        if (!passwordEncoder.matches(contrasena, user.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user.getCredencial(), user.getRol());
        return ResponseEntity.ok(token);
    }

    public User register(String nombre, String apellido, String credencial,
            String email, String contrasena, String rol) {

        // Validar que el email no exista
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Validar que la credencial no exista
        if (userRepository.findByCredencial(credencial) != null) {
            throw new RuntimeException("La credencial ya está registrada");
        }

        User newUser = new User();
        newUser.setNombre(nombre);
        newUser.setApellido(apellido);
        newUser.setCredencial(credencial);
        newUser.setEmail(email);
        newUser.setContrasena(passwordEncoder.encode(contrasena)); // Encriptar password
        newUser.setRol(rol.toUpperCase()); // Asegurar mayúsculas

        return userRepository.save(newUser);
    }
}
