
package com.app.Producto.dto;

import java.math.BigDecimal;

import com.app.Producto.entity.Producto;
import com.app.Producto.enums.Categoria;
import com.app.Producto.mappers.IMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoAgregarDto implements IMapper<Producto> {

    private String nombre;
    private String codigoDeBarras;
    private Categoria categoria;
    private String descripcion;
    private String marca;
    private BigDecimal precioUnitario;
    private String imagenUrl;
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
