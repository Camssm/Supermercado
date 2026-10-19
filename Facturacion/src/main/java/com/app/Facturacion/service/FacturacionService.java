package com.app.Facturacion.service;

import java.util.List;

import com.app.Facturacion.dto.FacturacionAgregarDto;
import com.app.Facturacion.dto.FacturacionDto;

public interface FacturacionService {

	FacturacionDto agregar(FacturacionAgregarDto facturacion, String cliente, String rol);

	List<FacturacionDto> listar(String cliente, String rol);

	FacturacionDto buscarPorId(Long id, String cliente, String rol);
}
