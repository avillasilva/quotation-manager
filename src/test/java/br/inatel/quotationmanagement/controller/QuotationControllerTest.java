package br.inatel.quotationmanagement.controller;

import static org.hamcrest.CoreMatchers.containsString;

import java.net.URI;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuotationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldCreateAQuotation() throws Exception {
		URI uri = new URI("/quotations");
		JSONObject quotation = new JSONObject();
		JSONObject quotes = new JSONObject();
		
		quotes.put("2021-08-17", "17");
		quotes.put("2021-08-12", "15");
		quotes.put("2021-08-11", "19");
		
		quotation.put("stockId", "petr4");
		quotation.put("quotes", quotes);
		
		System.out.println(quotes.toString());
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(quotation.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status().isCreated());
	}
	
	@Test
	public void shouldReturnTheQuotationsOfTheSpecifiedStockId() throws Exception {
		URI uri = new URI("/quotations/petr4");
				
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}
	
	@Test
	public void shouldReturnAllQuotations() throws Exception {
		URI uri = new URI("/quotations");
		
		mockMvc
		.perform(MockMvcRequestBuilders.get(uri))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}
}
