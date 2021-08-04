package br.inatel.quotationmanagement.acceptance.steps;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.json.JSONObject;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.inatel.quotationmanagement.controller.QuotationController;
import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuotationRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuotationSteps {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions response;
    
    private JSONObject quotation;
    private JSONObject quotes;
	
    @Before
    public void setup() {
        quotation = new JSONObject();
        quotes = new JSONObject();
    }
	
	@Given("a quotation with the stock id {string}")
	public void a_quotation_with_the_stock_id(String stockId) throws Exception {
		quotation.put("stockId", stockId);
	}
	
	@And("a quote with date {string} and value {string}")
	public void a_quote_with_date_and_value(String date, String value) throws Exception {
		quotes.put(date, value);
	}
	
	@When("I send the requests to register the quotations")
	public void i_send_the_request_to_register_the_quotations() throws Exception {
        quotation.put("quotes", quotes);
        
        response = mockMvc.perform(MockMvcRequestBuilders
            .post("/quotations")
            .content(quotation.toString())
            .contentType(MediaType.APPLICATION_JSON));
	}
	
	@Then("the response code should be {string}")
	public void the_response_code_should_be(String code) throws Exception {
        response.andExpect(MockMvcResultMatchers.status().is(201));
    }
}
