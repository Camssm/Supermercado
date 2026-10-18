package com.app.Facturacion.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.mappers.IMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturacionAgregarDto implements IMapper<Facturacion> {

    @NotBlank(message = "El cliente es obligatorio")
    @Size(max = 150, message = "El nombre del cliente no puede superar los 150 caracteres")
    private String cliente;

    @NotEmpty(message = "La factura debe tener al menos un producto")
    @Valid
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