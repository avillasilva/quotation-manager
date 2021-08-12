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
	
	@Autowired
	public StockCacheController(StockService stockService) {
		this.stockService = stockService;
		
//		log.info("Calling register method.");	
		this.stockService.registerApplication();
	}
	
	@DeleteMapping("/stockcache")
	public ResponseEntity<StockDto> cleanCache() {
		stockService.clearCache();
		return ResponseEntity.ok().build();
	}
	
//	public void registerApplication() {
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject notificationObject = new JSONObject();
//		notificationObject.put("host", "localhost");
//		notificationObject.put("port", 8081);
//		HttpEntity<String> request = new HttpEntity<String>(notificationObject.toString(), headers);
//		String response = restTemplate.postForObject("http://localhost:8080/notification", request, String.class);
//		System.out.println(response);
//	}
}
