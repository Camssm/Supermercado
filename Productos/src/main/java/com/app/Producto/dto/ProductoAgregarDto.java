package com.app.Producto.dto;

import java.math.BigDecimal;

import com.app.Producto.entity.Producto;
import com.app.Producto.enums.Categoria;
import com.app.Producto.mappers.IMapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoAgregarDto implements IMapper<Producto> {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombre;

    private String codigoDeBarras;

    @NotNull(message = "La categoría es obligatoria")
    private Categoria categoria;

    private String descripcion;
    private String marca;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal precioUnitario;

    private String imagenUrl;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @Override
    public Producto mapperTo() {
        return new Producto(
            null,
            nombre,
            codigoDeBarras,
            categoria,
            descripcion,
            marca,
            precioUnitario,
            imagenUrl,
            stock != null ? stock : 0
        );
    }
}