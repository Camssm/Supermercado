package com.app.Producto.exception;

import org.springframework.http.HttpStatus;

public class ProductoNoEncontradoException extends BusinessException {

    public ProductoNoEncontradoException(Long id) {
        super("P-404", HttpStatus.NOT_FOUND, "Producto no encontrado: " + id);
    }
}