package com.app.Administracion.service;

import java.util.List;

import com.app.Administracion.entity.Administracion;

public interface AdministracionService {

	public List<Administracion> listar();

	void agregar(Administracion administracion);

}
