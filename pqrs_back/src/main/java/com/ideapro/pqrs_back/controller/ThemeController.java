package com.ideapro.pqrs_back.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThemeController {

    @PostMapping("/toggle-theme")
    public String toggleTheme(HttpSession session) {
        String currentTheme = (String) session.getAttribute("theme");
        if ("dark".equals(currentTheme)) {
            session.setAttribute("theme", "light");
        } else {
            session.setAttribute("theme", "dark");
        }

        // Redirige a la página anterior si existe, o al home "/"
        String referer = (String) session.getAttribute("referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}
