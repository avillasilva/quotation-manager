package br.inatel.quotationmanagement.controller;

import static org.hamcrest.CoreMatchers.containsString;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.service.StockService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuotationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private List<StockDto> stocks;

    @BeforeEach
    public void setup() {
        stocks = new ArrayList<>();
        stocks.add(new StockDto("petr4", "Petrobras PN"));
        stocks.add(new StockDto("vale5", "Vale do Rio Doce PN"));
    }

    @Test
    public void shouldCreateAQuotation() throws Exception {
        Mockito.when(stockService.validate("petr4")).thenReturn(stocks.get(0));

        URI uri = new URI("/quotations");
        JSONObject quotation = new JSONObject();
        JSONObject quotes = new JSONObject();

        quotes.put("2021-08-17", "17");
        quotes.put("2021-08-12", "15");
        quotes.put("2021-08-11", "19");

        quotation.put("stockId", "petr4");
        quotation.put("quotes", quotes);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(quotation.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(containsString("id")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("quotes")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("2021-08-17")));
    }

    @Test
    public void shouldReturnTheQuotationsOfTheSpecifiedStockId() throws Exception {
        URI uri = new URI("/quotations/petr4");

        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("id")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
    }

    @Test
    public void shouldReturnAllQuotations() throws Exception {
        URI uri = new URI("/quotations");

        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("id")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
    }

}
