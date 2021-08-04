package br.inatel.quotationmanagement.acceptance.steps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.QuotationController;
import br.inatel.quotationmanagement.controller.dto.QuotationDto;
import br.inatel.quotationmanagement.controller.form.QuotationForm;
import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuotationRepository;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.service.Stock;
import br.inatel.quotationmanagement.service.StockService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuotationSteps {
	
	@Mock
	private StockService stockService;
	
	@Mock
	private QuotationRepository quotationRepository;
	
	@Mock
	private QuoteRepository quoteRepository;
	
    private QuotationController quotationController;
    
    private QuotationForm quotationForm;
    private ResponseEntity<QuotationDto> response;
    
    @Given("a quotation with the stock id {string}")
    public void a_quotation_with_the_stock_id(String stockId) throws Exception {
    	MockitoAnnotations.openMocks(this);
    	
    	Quotation quotation = new Quotation("petr4");
    	List<Quotation> quotations = new ArrayList<Quotation>();
    	quotations.add(quotation);
    	
    	List<Quote> quotes = new ArrayList<Quote>();
    	quotes.add(new Quote(LocalDate.of(2021, 8, 12), new BigDecimal("14"), quotation));
    	quotes.add(new Quote(LocalDate.of(2021, 8, 16), new BigDecimal("13"), quotation));
    	
    	Mockito.when(stockService.getStock("petr4")).thenReturn(new Stock("petr4"));
    	Mockito.when(quotationRepository.findAllByStockId("petr4")).thenReturn(Optional.of(quotations));
    	
    	quotationController = new QuotationController(stockService, quotationRepository, quoteRepository);
    	quotationForm = new QuotationForm();
    	quotationForm.setStockId(stockId);
    	quotationForm.setQuotes(new HashMap<LocalDate, BigDecimal>());
    }
    
    @And("a {int} quotes to be added")
    public void a_quotes_to_be_added(int numberOfQuotes) {
    	int hundredYears = 100 * 365;
    	for(int i = 0; i < numberOfQuotes; i++) {
    	    LocalDate date = LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(-hundredYears, hundredYears));
    	    quotationForm.getQuotes().put(date, new BigDecimal("15"));
    	}
    }
	
	@When("I send the request to register the quotations")
	public void i_send_the_request_to_register_the_quotations() throws Exception {
		response = quotationController.create(quotationForm, UriComponentsBuilder.newInstance());
	}

	@Then("the response http status should be {int}")
	public void the_response_http_code_should_be_code(int code) {
		Assert.assertEquals(code, response.getStatusCode().value());
	}
}
