package br.inatel.quotationmanagement.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {
	public List<Stock> getAllStocks() {
		RestTemplate restTemplate = new RestTemplate();
		
		List<Stock> stocks = restTemplate.getForObject("locahost:8080/stocks", List.class);
		return stocks;
	}
	
	public void createStock(Stock stock) throws Exception {
		RestTemplate restTemplate = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject stockObject = new org.json.JSONObject();
		stockObject.put("id", stock.getId());
		stockObject.put("description", stock.getDescription());
		
		HttpEntity<String> request = new HttpEntity<String>(stockObject.toString(), headers);
		String response = restTemplate.postForObject("localhost:8080/stock", request, String.class);
		System.out.println(response);
	}
}
