package br.inatel.quotationmanagement;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class QuotationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotationManagementApplication.class, args);
		registerApplication();
	}

	private static void registerApplication() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject notificationObject = new JSONObject();
		notificationObject.put("host", "localhost");
		notificationObject.put("port", 8081);
		HttpEntity<String> request = new HttpEntity<String>(notificationObject.toString(), headers);
		String response = restTemplate.postForObject("http://localhost:8080/notification", request, String.class);
		System.out.println(response);
	}
}
