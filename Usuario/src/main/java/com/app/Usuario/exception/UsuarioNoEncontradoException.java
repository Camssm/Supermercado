package com.app.Usuario.exception;

import org.springframework.http.HttpStatus;

public class UsuarioNoEncontradoException extends BusinessException {

    public UsuarioNoEncontradoException(String nombreCompleto) {
        super(
            "U-404",
            HttpStatus.NOT_FOUND,
            "Usuario no encontrado: " + nombreCompleto
        );
    }
}
