package br.inatel.quotationmanagement.acceptance.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.QuotationController;
import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.controller.form.QuotationForm;
import br.inatel.quotationmanagement.controller.form.QuoteForm;
import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuotationRepository;
import br.inatel.quotationmanagement.repository.QuoteRepository;
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
    private ResponseEntity<?> response;
    
    @Given("Some quotations registered in the database")
    public void setup() {
    	MockitoAnnotations.openMocks(this);
    	
    	Quotation quotation = new Quotation("petr4");
    	List<Quotation> quotations = new ArrayList<Quotation>();
    	quotations.add(quotation);
    	
    	List<Quote> quotes = new ArrayList<Quote>();
    	quotes.add(new Quote(LocalDate.of(2021, 8, 12), new BigDecimal("14"), quotation));
    	quotes.add(new Quote(LocalDate.of(2021, 8, 16), new BigDecimal("13"), quotation));
    	
    	Mockito.when(stockService.getStock("petr4")).thenReturn(new StockDto("petr4", "Petrobras PN"));
    	Mockito.when(quotationRepository.findAllByStockId("petr4")).thenReturn(Optional.of(quotations));
    	
    	quotationController = new QuotationController(stockService, quotationRepository, quoteRepository);
    }
    
    @And("a quotation with the stock id {string}")
    public void a_quotation_with_the_stock_id(String stockId) throws Exception {
    	quotationForm = new QuotationForm();
    	quotationForm.setStockId(stockId);
    	quotationForm.setQuotes(new ArrayList<QuoteForm>());
    }
    
    @And("a {int} quotes to be added")
    public void a_quotes_to_be_added(int numberOfQuotes) {
    	int hundredYears = 100 * 365;
    	for(int i = 0; i < numberOfQuotes; i++) {
    	    LocalDate date = LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(-hundredYears, hundredYears));
    	    quotationForm.getQuotes().add(new QuoteForm(date.toString(), "15.00"));
    	}
    }
	
	@When("I send the request to register the quotations")
	public void i_send_the_request_to_register_the_quotations() throws Exception {
		response = quotationController.create(quotationForm, UriComponentsBuilder.newInstance());
	}

	@Then("the response http status should be {int}")
	public void the_response_http_code_should_be_code(int code) {
		assertEquals(code, response.getStatusCode().value());
	}
	
	@When("I try to list the quotes with the stock id {string}")
	public void i_try_to_list_the_quotes_with_the_stock_id(String stockId) {
		response = quotationController.getByStockId(stockId);
	}
}
