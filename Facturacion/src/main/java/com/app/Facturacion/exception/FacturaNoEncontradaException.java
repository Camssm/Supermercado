package com.app.Facturacion.exception;

import org.springframework.http.HttpStatus;

public class FacturaNoEncontradaException extends BusinessException {

    public FacturaNoEncontradaException(Long id) {
        super("F-404", HttpStatus.NOT_FOUND, "No existe la factura con id " + id);
    }
}