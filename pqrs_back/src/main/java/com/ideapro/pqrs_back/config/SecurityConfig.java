package com.ideapro.pqrs_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ideapro.pqrs_back.auth.security.JwtFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            .csrf(csrf -> csrf.disable())
            
            // ← AGREGAR CONFIGURACIÓN DE SESIONES
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authorizeHttpRequests(auth -> auth
                // ← CAMBIAR ESTAS RUTAS POR LAS QUE REALMENTE TIENES
                .requestMatchers("/auth/**").permitAll()  // Para /auth/login y /auth/register
                .requestMatchers("/error").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/formulario").permitAll()
                .requestMatchers("/consultar").permitAll()
                .requestMatchers("/dashboard").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/api/peticionarios").permitAll()
                .requestMatchers("/api/pqrs").permitAll()
                .anyRequest().authenticated()
            )

            // Filtro JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}