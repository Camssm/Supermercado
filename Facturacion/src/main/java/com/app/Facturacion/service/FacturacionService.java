package com.app.Facturacion.service;

import java.util.List;

import com.app.Facturacion.entity.Facturacion;

public interface FacturacionService {

	public List<Facturacion> listar();

	void agregar(Facturacion facturacion);

}
