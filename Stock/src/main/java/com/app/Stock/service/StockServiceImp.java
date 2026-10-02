package com.app.Stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Stock.entity.Stock;
import com.app.Stock.repository.StockRepository;

@Service
public class StockServiceImp implements StockService{

	@Autowired
	StockRepository stockRepository;
	
	@Override
	public List<Stock> listar() {
		
		List<Stock> registros = this.stockRepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Stock stock) {
        this.stockRepository.save(stock);
	}
}
