package com.ideapro.pqrs_back.pqrs.Security;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PqrsDetailsImpl implements UserDetails {
    private final Pqrs pqrs;

    public PqrsDetailsImpl(Pqrs pqrs) {
        this.pqrs = pqrs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Puedes personalizar los roles según tu lógica
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // Si tu PQRS tiene algún campo de autenticación, retorna aquí
        return null;
    }

    @Override
    public String getUsername() {
        // Por ejemplo, el número de radicado
        return pqrs.getNumeroRadicado();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Pqrs getPqrs() {
        return pqrs;
    }
}