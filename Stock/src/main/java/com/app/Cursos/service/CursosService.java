package com.app.Cursos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Cursos.entity.Cursos;

public interface CursosService {

public List<Cursos> listar();
    
    public void agregar(Cursos cursos);
}
