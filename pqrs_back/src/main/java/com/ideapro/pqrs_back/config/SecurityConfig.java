package com.ideapro.pqrs_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ideapro.pqrs_back.auth.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            // Política de sesión sin estado (JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (controladores y recursos estáticos)
                .requestMatchers(
                    "/auth/**",      // Login, registro vía API
                    "/error",        // Página de error
                    "/",             // Página principal
                    "/login",        // Vista login
                    "/register",     // Vista registro
                    "/dashboard",    // Vista dashboard
                    "/formulario",   // Vista formulario
                    "/css/**",       // Archivos CSS
                    "/js/**",        // Archivos JS
                    "/images/**"     // Imágenes
                ).permitAll()

                // El resto requiere autenticación
                .anyRequest().authenticated()
            )

            // Filtro JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
