package br.inatel.quotationmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.service.StockService;

@RestController
public class StockCacheController {
	
	@Autowired
	private StockService stockService;
	
	@DeleteMapping("/stockcache")
	public ResponseEntity<StockDto> cleanCache() {
		
		System.out.println("Clearing cache...");
		
		stockService.clearCache();
		return ResponseEntity.ok().build();
	}
}
