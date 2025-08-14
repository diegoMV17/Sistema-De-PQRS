package com.ideapro.pqrs_back.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalViewData {

    @ModelAttribute("navItems")
    public List<Map<String, String>> navItems() {
        return List.of(
            Map.of("id", "home", "name", "Inicio", "href", "/"),
             Map.of("id", "info", "name", "Informacion", "href", "/#info")
            /**Map.of("id", "formulario", "name", "Formulario PQRS", "href", "/formulario"),**/
     
        );
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated()
               && !"anonymousUser".equals(auth.getPrincipal());
    }

    @ModelAttribute("userEmail")
    public String userEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
            && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "";
    }
}
