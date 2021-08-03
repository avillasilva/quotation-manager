package br.inatel.quotationmanagement.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {
	
	@Cacheable("stocks")
	public Stock[] getAllStocks() {
//		try {
//			System.out.println("Retrieving all the stocks...");
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		 	
		RestTemplate restTemplate = new RestTemplate();
		Stock[] stocks = restTemplate.getForObject("http://localhost:8080/stock", Stock[].class);
		return stocks;
	}
	
	@CacheEvict(value = "stocks", allEntries = true)
	public void clearCache() {}
	
//	public void createStock(Stock stock) throws Exception {
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject stockObject = new org.json.JSONObject();
//		stockObject.put("id", stock.getId());
//		stockObject.put("description", stock.getDescription());
//		
//		HttpEntity<String> request = new HttpEntity<String>(stockObject.toString(), headers);
//		String response = restTemplate.postForObject("http://localhost:8080/stock", request, String.class);
//		System.out.println(response);
//	}
}
