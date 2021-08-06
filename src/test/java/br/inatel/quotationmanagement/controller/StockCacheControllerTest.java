package br.inatel.quotationmanagement.controller;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StockCacheControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldCleanTheCache() throws Exception {
		URI uri = new URI("/stockcache");
		
		mockMvc
			.perform(MockMvcRequestBuilders.delete(uri))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
