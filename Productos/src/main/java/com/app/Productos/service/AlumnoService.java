package com.app.Productos.service;

import java.util.List;

import com.app.Productos.entity.Alumno;

public interface AlumnoService {
	
    public List<Alumno> listar();
    
    public void agregar(Alumno alumno);
}