
package com.app.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {

	private static final List<String> OPEN_API_ENDPOINTS = List.of(
 
			"/auth/login",
	        "/auth/registrar",
	        "/api/auth/login",
	        "/api/auth/registrar",
	        "/api/usuario/validar-login",
	        "/api/usuario/registro",
	        "/api/usuario/login",
	        "/api/usuario/registrar",
	        "/auth/v3/api-docs",
	        "/api/productos/v3/api-docs",
	        "/api/facturas/v3/api-docs",
	        "/api/usuario/v3/api-docs",
	        "/swagger-ui",
	        "/webjars",
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
