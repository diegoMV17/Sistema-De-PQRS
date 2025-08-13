package com.ideapro.pqrs_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.service.UserService;
import com.ideapro.pqrs_back.user.exception.UserException;
import com.ideapro.pqrs_back.user.Security.UserDetailsImpl;

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
    public ResponseEntity<String> eliminarUser(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       
        if (!"ADMIN".equalsIgnoreCase(userDetails.getRol())) {
            return ResponseEntity.status(403).body("No tiene permisos para eliminar usuarios");
        }

       
        if (userDetails.getId().equals(id)) {
            return ResponseEntity.badRequest().body("No puede eliminar su propio usuario");
        }

       
        User usuarioAEliminar = userService.obtenerUser(id);
        if (usuarioAEliminar == null) {
            return ResponseEntity.badRequest().body("El usuario que intenta eliminar no existe");
        }

       
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
        return userService.login(user.getCredencial(), user.getContrasena());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
