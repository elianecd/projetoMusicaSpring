package com.example.projetomusicafinal.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    protected final String[] excludedUrls = {"/usuarios/login",  "/usuarios/novo-registro"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Verificar se a requisição é para uma URL excluída
        String requestUri = request.getRequestURI();
        if (Arrays.asList(excludedUrls).contains(requestUri)) {
            // Permitir a requisição sem verificar o token JWT
            return true;
        }

        // Verificar se o token JWT está presente na requisição
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            // Token JWT ausente ou malformatado
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{ \"message\": \"Token ausente na requisição.\" }");
            return false;
        }
        // Token JWT presente, permitir que a requisição continue
        return true;
    }
}
