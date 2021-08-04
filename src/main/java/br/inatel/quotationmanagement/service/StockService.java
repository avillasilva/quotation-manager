package br.inatel.quotationmanagement.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {
	
	@Value("${stock-manager.url}")
	private String API_URL;
	
	@Cacheable("stocks")
	public Stock[] getAllStocks() {

//		try {
//			System.out.println("Retrieving all the stocks...");
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		 	
		RestTemplate restTemplate = new RestTemplate();
		Stock[] stocks = restTemplate.getForObject(API_URL + "/stock", Stock[].class);
		return stocks;
	}
	
	@CacheEvict(value = "stocks", allEntries = true)
	public void clearCache() {}
	
	@Cacheable("stock")
	public Stock getStock(String stockId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = API_URL + "/stock/" + stockId;
		return restTemplate.getForObject(url, Stock.class);
	}
	
	public void registerApplication() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject notificationObject = new JSONObject();
		notificationObject.put("host", "localhost");
		notificationObject.put("port", 8081);
		HttpEntity<String> request = new HttpEntity<String>(notificationObject.toString(), headers);
		String response = restTemplate.postForObject(API_URL +  "/notification", request, String.class);
		System.out.println(response);
	}
	
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
