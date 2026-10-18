package com.app.Facturacion.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.mappers.IMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturacionAgregarDto implements IMapper<Facturacion> {

    private String cliente;
    private List<DetalleFacturaAgregarDto> detalles = new ArrayList<>();

    @Override
    public Facturacion mapperTo() {
        Facturacion facturacion = new Facturacion();

        facturacion.setCliente(cliente);

        for (DetalleFacturaAgregarDto dto : detalles) {
            facturacion.getDetalles().add(dto.mapperTo());
        }

        return facturacion;
    }
}