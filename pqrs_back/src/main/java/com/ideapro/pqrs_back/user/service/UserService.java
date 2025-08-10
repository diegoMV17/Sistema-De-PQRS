package com.ideapro.pqrs_back.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
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
    
}
