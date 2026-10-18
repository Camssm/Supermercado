package com.app.Facturacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Facturacion.dto.FacturacionAgregarDto;
import com.app.Facturacion.dto.FacturacionDto;
import com.app.Facturacion.service.FacturacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/facturacion")
public class FacturacionController {

	@Autowired
	private FacturacionService facturacionService;

	@RequestMapping(value = "/facturas", method = RequestMethod.GET)
	public ResponseEntity<List<FacturacionDto>> listar() {

		return ResponseEntity.ok(facturacionService.listar());
	}

	@RequestMapping(value = "/facturas/{id}", method = RequestMethod.GET)
	public ResponseEntity<FacturacionDto> buscarPorId(@PathVariable Long id) {

		return ResponseEntity.ok(facturacionService.buscarPorId(id));
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.PUT)
	public ResponseEntity<FacturacionDto> agregar(@Valid @RequestBody FacturacionAgregarDto facturaDto) {

		FacturacionDto factura = facturacionService.agregar(facturaDto);

		return new ResponseEntity<>(factura, HttpStatus.CREATED);
	}
}