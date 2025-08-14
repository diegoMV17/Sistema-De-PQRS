package com.ideapro.pqrs_back.peticionario.Security;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PeticionarioDetailsImpl implements UserDetails {
    private final Peticionario peticionario;

    public PeticionarioDetailsImpl(Peticionario peticionario) {
        this.peticionario = peticionario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Puedes personalizar los roles según tu lógica
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // Si tu entidad Peticionario tiene contraseña, retorna aquí
        return null;
    }

    @Override
    public String getUsername() {
        // Por ejemplo, el email o documento del peticionario
        return peticionario.getEmail();
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

    public Peticionario getPeticionario() {
        return peticionario;
    }
}