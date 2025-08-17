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

    @PostMapping
    @PreAuthorize("hasAuthority(ADMIN)")
    public User crearUser(@RequestBody User user) {
        return userService.crearUser(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> listarUser() {
        return userService.listarUser();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User obtenerUser(@PathVariable Long id) {
        return userService.obtenerUser(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminarUser(@PathVariable Long id) {
        userService.eliminarUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    @GetMapping("/buscarPorEmail/{email}")
    public List<User> buscarEmail(@PathVariable String email) {
        return userService.buscarEmail(email);
    }

       //NO NECESITA AUTORIZACION
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

       //NO NECESITA AUTORIZACION
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getCredencial(), user.getContrasena());
    }

}
