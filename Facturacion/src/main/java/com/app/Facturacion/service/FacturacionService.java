package com.app.Facturacion.service;

import java.util.List;

import com.app.Facturacion.dto.FacturacionAgregarDto;
import com.app.Facturacion.dto.FacturacionDto;

public interface FacturacionService {

	FacturacionDto agregar(FacturacionAgregarDto facturacion);

	List<FacturacionDto> listar();

	FacturacionDto buscarPorId(Long id);
}