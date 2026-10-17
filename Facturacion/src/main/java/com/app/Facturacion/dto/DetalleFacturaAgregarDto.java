package com.app.Facturacion.dto;

import com.app.Facturacion.entity.DetalleFactura;
import com.app.Facturacion.mappers.IMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleFacturaAgregarDto implements IMapper<DetalleFactura> {

    private Long productoId;
    private Integer cantidad;

    @Override
    public DetalleFactura mapperTo() {
        DetalleFactura detalle = new DetalleFactura();

        detalle.setProductoId(productoId);
        detalle.setCantidad(cantidad);

        return detalle;
    }
}