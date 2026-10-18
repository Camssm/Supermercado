
package com.app.Producto.dto;

import java.math.BigDecimal;

import com.app.Producto.entity.Producto;
import com.app.Producto.enums.Categoria;
import com.app.Producto.mappers.IMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoEditarDto implements IMapper<Producto> {

    private Long id;
    private String nombre;
    private String codigoDeBarras;
    private Categoria categoria;
    private String descripcion;
    private String marca;
    private BigDecimal precioUnitario;
    private String imagenUrl;

    @Override
    public Producto mapperTo() {
        return new Producto(
            id,
            nombre,
            codigoDeBarras,
            categoria,
            descripcion,
            marca,
            precioUnitario,
            imagenUrl,
            0
        );
    }
}
