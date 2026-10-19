package com.app.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.app.security.dto.AuthRequest;
import com.app.security.dto.AuthResponse;
import com.app.security.dto.RegistroRequest;
import com.app.security.service.AuthService;
import com.app.security.service.CredencialesInvalidasException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación",
     description = "Endpoints para generación de tokens JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Iniciar sesión para obtener el token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (CredencialesInvalidasException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroRequest request) {
        try {
            authService.registrar(request);   // delega en usuarioClient.registrar(request)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        }
    }
}