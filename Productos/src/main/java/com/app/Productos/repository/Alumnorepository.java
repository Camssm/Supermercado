package com.app.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Productos.entity.Alumno;

public interface Alumnorepository extends JpaRepository<Alumno, Integer> {

	
}
