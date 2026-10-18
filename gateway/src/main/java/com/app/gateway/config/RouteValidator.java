package com.app.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {

    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/auth/login",
            "/api/auth/registrar",
            "/api/usuario/login",
            "/api/usuario/registrar",
            "/actuator"
    );

    public boolean isSecured(ServerHttpRequest request) {
        String path = request.getURI().getPath();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod().name())) {
            return false;
        }

        return OPEN_API_ENDPOINTS.stream()
                .noneMatch(path::startsWith);
    }
}