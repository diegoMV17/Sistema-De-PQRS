package com.ideapro.pqrs_back.user.Security;

import com.ideapro.pqrs_back.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String role; // Ej: "ADMIN", "USER", etc.

    // Constructor a partir de tu entidad User
    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getContrasena();
        this.role = user.getRol();
    }

    // Getter explícito para usar getRol() en tu código
    public String getRol() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Prefijo ROLE_ obligatorio para Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return email; // Aquí usamos email como "username"
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambia si tienes lógica para expirar cuentas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia si manejas bloqueo de cuentas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambia si caducan credenciales
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambia si manejas estado activo/inactivo
    }
}
