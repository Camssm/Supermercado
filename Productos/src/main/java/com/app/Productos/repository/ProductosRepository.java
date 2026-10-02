package com.app.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Productos.entity.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {

	
}
