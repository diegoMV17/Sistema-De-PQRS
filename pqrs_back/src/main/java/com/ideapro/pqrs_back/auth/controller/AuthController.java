package com.ideapro.pqrs_back.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideapro.pqrs_back.auth.service.AuthService;
import com.ideapro.pqrs_back.user.model.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        String token = authService.login(request.getEmail(), request.getContrasena());

        if (token != null) {
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "message", "Login exitoso"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales inválidas"));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor"));
    }
}



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = authService.register(
                    request.getNombre(),
                    request.getApellido(),
                    request.getCredencial(),
                    request.getEmail(),
                    request.getContrasena(),
                    request.getRol());

            return ResponseEntity.ok(Map.of(
                    "message", "Usuario creado exitosamente",
                    "userId", newUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String contrasena;
    }
    @Data
    public static class RegisterRequest {
    private String nombre;
    private String apellido;
    private String credencial;
    private String email;
    private String contrasena;
    private String rol; // "ADMIN" o "FUNCIONARIO"
}
}
