package com.ideapro.pqrs_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Solo ADMIN puede crear usuarios
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public User crearUser(@RequestBody User user) {
        return userService.crearUser(user);
    }

    // Solo ADMIN puede listar usuarios
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> listarUser() {
        return userService.listarUser();
    }

    // Solo ADMIN puede consultar usuarios por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User obtenerUser(@PathVariable Long id) {
        return userService.obtenerUser(id);
    }

    // Solo ADMIN puede eliminar
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminarUser(@PathVariable Long id) {
        userService.eliminarUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    // Buscar por email (abierto a todos, puede ser útil en login/consultas)
    @GetMapping("/buscarPorEmail/{email}")
    public List<User> buscarEmail(@PathVariable String email) {
        return userService.buscarEmail(email);
    }

    // Registro de usuario (ABIERTO, sin autorización)
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // Login de usuario (ABIERTO)
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getCredencial(), user.getContrasena());
    }
}
