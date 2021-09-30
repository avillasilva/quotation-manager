package br.inatel.quotationmanagement.service;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.exception.StockNotFoundException;

@Service
public class StockService {
	
	@Value("${stock-manager.url}")
	private String API_URL;
	
	@Value("${server.port}")
	private int serverPort;
	
	@Cacheable("stocks")
	public StockDto[] getAllStocks() { 	
		RestTemplate restTemplate = new RestTemplate();
		StockDto[] stocks = restTemplate.getForObject(API_URL + "/stock", StockDto[].class);
		return stocks;
	}
	
	@CacheEvict(value = { "stocks", "stock" }, allEntries = true)
	public void clearCache() {}
	
	@Cacheable("stock")
	public StockDto validate(String stockId) throws StockNotFoundException {
		String url = API_URL + "/stock/" + stockId;
		RestTemplate restTemplate = new RestTemplate();
		StockDto stockDto = restTemplate.getForObject(url, StockDto.class);
		
		if (stockDto == null) {
			throw new StockNotFoundException("There is no stock in the database with id " + stockId);
		}
		
		return stockDto;
	}

	@EventListener(ApplicationReadyEvent.class)
    public void registerApplication() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> notification = new HashMap<String, String>();
        notification.put("host", "localhost");
        notification.put("port", String.valueOf(serverPort));

        String jsonString = new ObjectMapper().writeValueAsString(notification);

		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
        String response = restTemplate.postForObject(API_URL +  "/notification", request, String.class);

        System.out.println("Registering application event... Response received: " + response);
    }
}
