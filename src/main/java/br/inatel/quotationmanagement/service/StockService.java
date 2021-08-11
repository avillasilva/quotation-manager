package br.inatel.quotationmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {
	
	@Value("${stock-manager.url}")
	private String API_URL;
	
	@Value("${server.port}")
	private int serverPort;
	
	@Cacheable("stocks")
	public Stock[] getAllStocks() { 	
		RestTemplate restTemplate = new RestTemplate();
		Stock[] stocks = restTemplate.getForObject(API_URL + "/stock", Stock[].class);
		return stocks;
	}
	
	@CacheEvict(value = { "stocks", "stock" }, allEntries = true)
	public void clearCache() {}
	
	@Cacheable("stock")
	public Stock getStock(String stockId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = API_URL + "/stock/" + stockId;
		return restTemplate.getForObject(url, Stock.class);
	}
}
