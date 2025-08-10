package com.ideapro.pqrs_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    // Buscar por ID
    @GetMapping("/{id}")
    public User obtenerUser(@PathVariable Long id) {
        return userService.obtenerUser(id);
    }
    // Eliminar por ID
    @PostMapping("/eliminar/{id}")
    public void eliminarUser(@PathVariable Long id) {
        userService.eliminarUser(id);
    }
    // Buscar por email
    @GetMapping("/buscarPorEmail/{email}")
    public List<User> buscarEmail(@PathVariable String email) {
        return userService.buscarEmail(email);
    }
}
