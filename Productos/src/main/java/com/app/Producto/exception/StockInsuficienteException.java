package com.app.Producto.exception;

import org.springframework.http.HttpStatus;

public class StockInsuficienteException extends BusinessException {

    public StockInsuficienteException(String nombreProducto) {
        super("P-409", HttpStatus.CONFLICT, "Stock insuficiente para el producto: " + nombreProducto);
    }
}