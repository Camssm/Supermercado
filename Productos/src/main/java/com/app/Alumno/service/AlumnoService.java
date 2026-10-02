package com.app.Alumno.service;

import java.util.List;
import com.app.Alumno.entity.Alumno;

public interface AlumnoService {
	
    public List<Alumno> listar();
    
    public void agregar(Alumno alumno);
}