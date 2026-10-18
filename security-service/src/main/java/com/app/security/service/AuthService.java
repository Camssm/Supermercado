package com.app.security.service;

import org.springframework.stereotype.Service;

import com.app.security.client.UsuarioClient;
import com.app.security.dto.AuthRequest;
import com.app.security.dto.AuthResponse;
import com.app.security.dto.UsuarioLoginResponse;

@Service
public class AuthService {

    private final UsuarioClient usuarioClient;
    private final JwtProvider jwtProvider;

    public AuthService(
            UsuarioClient usuarioClient,
            JwtProvider jwtProvider) {
        this.usuarioClient = usuarioClient;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponse login(AuthRequest request) {
        UsuarioLoginResponse usuario = usuarioClient.validar(request);

        if (usuario == null) {
            throw new CredencialesInvalidasException();
        }

        String token = jwtProvider.createToken(
                usuario.getNombreCompleto(),
                usuario.getRol());

        return new AuthResponse(token);
    }
}