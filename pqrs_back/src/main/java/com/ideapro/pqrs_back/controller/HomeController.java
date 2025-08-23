package com.ideapro.pqrs_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private String getThemeFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("theme".equals(cookie.getName()) && "dark".equals(cookie.getValue())) {
                    return "dark";
                }
            }
        }
        return "light";
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("currentPage", "home");
        model.addAttribute("theme", getThemeFromCookies(request));
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("currentPage", "login");
        model.addAttribute("theme", getThemeFromCookies(request));
        return "login";
    }

    @GetMapping("/register")
    public String registro(Model model,HttpServletRequest request) {
        model.addAttribute("currentPage", "register");
        model.addAttribute("theme", getThemeFromCookies(request));
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("currentPage", "dashboard");
        return "dashboard";
    }

    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("currentPage", "formulario");
        return "formulario";
    }

    @GetMapping("/consultar")
    public String consultar(Model model) {
        model.addAttribute("currentPage", "consultar");
        return "consultar";
    }
}
