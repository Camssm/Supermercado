package com.escuela.security.controller;

import com.escuela.security.dto.AuthRequest;
import com.escuela.security.dto.AuthResponse;
import com.escuela.security.service.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para generación de tokens JWT")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Operation(summary = "Iniciar sesión para obtener el token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        if ("admin".equals(request.getUsername())
                && "1234".equals(request.getPassword())) {

            String token = jwtProvider.createToken(request.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales incorrectas");
    }
}