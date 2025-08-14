package com.ideapro.pqrs_back.peticionario.Security;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PeticionarioService implements UserDetailsService {

    @Autowired
    private PeticionarioRepository peticionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Peticionario peticionario = peticionarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Peticionario no encontrado con email: " + email));
        return new PeticionarioDetailsImpl(peticionario);


    }
}