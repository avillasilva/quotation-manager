package br.inatel.quotationmanagement.util.listeners;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationRegisterListener {

    @Value("${stock-manager.url}")
    private String API_URL;

    @Value("${server.port}")
    private int serverPort;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws JsonProcessingException {
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