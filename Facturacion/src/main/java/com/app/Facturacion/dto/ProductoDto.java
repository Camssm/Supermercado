package com.app.Facturacion.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoDto {

    private Long id;
    private String nombre;
    private BigDecimal precioUnitario;
    private Integer stock;
}
