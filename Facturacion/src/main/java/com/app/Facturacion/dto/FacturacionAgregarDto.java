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

	@NotEmpty(message = "La factura debe tener al menos un producto")
	@Valid
	private List<@Valid DetalleFacturaAgregarDto> detalles = new ArrayList<>();

	@Override
	public Facturacion mapperTo() {
		Facturacion facturacion = new Facturacion();

		for (DetalleFacturaAgregarDto dto : detalles) {
			facturacion.getDetalles().add(dto.mapperTo());
		}

		return facturacion;
	}
}