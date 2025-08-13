package com.ideapro.pqrs_back.user.Security;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por email
        List<User> results = userRepository.findByEmail(username);

        if (results.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        User user = results.get(0); // Obtenemos el usuario directamente
        return new UserDetailsImpl(user);
    }
}
