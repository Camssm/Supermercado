package com.app.Facturacion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturacionDto {

    private Long id;
    private LocalDateTime fecha;
    private String cliente;
    private BigDecimal total;
    private List<DetalleFacturaDto> detalles = new ArrayList<>();
}