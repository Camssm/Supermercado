package com.app.Facturacion.dto;

import com.app.Facturacion.entity.DetalleFactura;
import com.app.Facturacion.mappers.IMapper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleFacturaAgregarDto implements IMapper<DetalleFactura> {

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser mayor que cero")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @Override
    public DetalleFactura mapperTo() {
        DetalleFactura detalle = new DetalleFactura();

        detalle.setProductoId(productoId);
        detalle.setCantidad(cantidad);

        return detalle;
    }
}