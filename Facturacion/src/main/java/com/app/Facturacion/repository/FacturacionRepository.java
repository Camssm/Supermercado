package com.app.Facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Facturacion.entity.Facturacion;


public interface FacturacionRepository extends JpaRepository<Facturacion, Integer>{

}
