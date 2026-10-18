package com.app.Usuario.exception;

import org.springframework.http.HttpStatus;

public class UsuarioDuplicadoException extends BusinessException {

    public UsuarioDuplicadoException(String email) {
        super("U-409", HttpStatus.CONFLICT, "Ya existe un usuario con el email " + email);
    }
}