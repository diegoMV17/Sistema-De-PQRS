package com.ideapro.pqrs_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.service.UserService;
import com.ideapro.pqrs_back.user.exception.UserException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User crearUser(@RequestBody User user) {
        return userService.crearUser(user);
    }

    @GetMapping
    public List<User> listarUser() {
        return userService.listarUser();
    }

    @GetMapping("/{id}")
    public User obtenerUser(@PathVariable Long id) {
        return userService.obtenerUser(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUser(@PathVariable Long id) {
        userService.eliminarUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    @GetMapping("/buscarPorEmail/{email}")
    public List<User> buscarEmail(@PathVariable String email) {
        return userService.buscarEmail(email);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getCredencial(), user.getConstrasena());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
