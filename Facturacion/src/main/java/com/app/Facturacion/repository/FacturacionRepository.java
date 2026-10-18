package com.app.Facturacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Facturacion.entity.Facturacion;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    List<Facturacion> findByClienteIgnoreCase(String cliente);

}
