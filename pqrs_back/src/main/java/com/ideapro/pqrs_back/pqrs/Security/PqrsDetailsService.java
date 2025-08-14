package com.ideapro.pqrs_back.pqrs.Security;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PqrsDetailsService implements UserDetailsService {

    @Autowired
    private PqrsRepository pqrsRepository;

    @Override
    public UserDetails loadUserByUsername(String numeroRadicado) throws UsernameNotFoundException {
        java.util.List<Pqrs> pqrsList = pqrsRepository.findByNumeroRadicado(numeroRadicado);
        if (pqrsList.isEmpty()) {
            throw new UsernameNotFoundException("PQRS no encontrado con radicado: " + numeroRadicado);
        }
        Pqrs pqrs = pqrsList.get(0);
        return new PqrsDetailsImpl(pqrs);
    }
}