package com.ideapro.pqrs_back.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ThemeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        // Guardar la URL anterior para redirigir después del toggle
        if (!uri.equals("/toggle-theme")) {
            session.setAttribute("referer", uri);
        }

        // Inicializar tema si no existe
        if (session.getAttribute("theme") == null) {
            session.setAttribute("theme", "light");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        if (modelAndView != null) {
            HttpSession session = request.getSession();
            modelAndView.addObject("theme", session.getAttribute("theme"));
        }
    }
}
