package com.ideapro.pqrs_back.auth.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ideapro.pqrs_back.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("🔍 Token extraído: " + token.substring(0, 20) + "...");

            try {
                String username = jwtUtil.extractUsername(token);
                System.out.println("🔍 Username del token: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userOptional = userRepository.findByEmail(username);
                    System.out.println("🔍 Usuario encontrado en BD: " + !userOptional.isEmpty());

                    if (!userOptional.isEmpty() && jwtUtil.validateToken(token)) {
                        var user = userOptional.get(0);
                        System.out.println("🔍 Rol del usuario: " + user.getRol());

                        List<SimpleGrantedAuthority> authorities =
                                List.of(new SimpleGrantedAuthority("ROLE_" +user.getRol()));

                        System.out.println("🔍 Autoridades creadas: " + authorities);

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        authorities
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("✅ Usuario autenticado correctamente");
                    } else {
                        System.out.println("❌ Token inválido o usuario no encontrado");
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Error procesando JWT: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ No hay header Authorization o no empieza con Bearer");
        }

        filterChain.doFilter(request, response);
    }
}