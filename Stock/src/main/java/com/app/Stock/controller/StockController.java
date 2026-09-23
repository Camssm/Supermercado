package com.app.Stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Stock.entity.Stock;
import com.app.Stock.service.StockService;

@RestController
@RequestMapping(value = "/api/cursos")
public class StockController {

		@Autowired
		private StockService stockService;
		
		@RequestMapping(value = "/cursos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Stock>> listar() 
		{
			return new ResponseEntity<>(stockService.listar(), HttpStatus.OK);
		}
		
		@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Stock>> agregar(@RequestBody Stock stock)
		{
			stockService.agregar(stock);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}

