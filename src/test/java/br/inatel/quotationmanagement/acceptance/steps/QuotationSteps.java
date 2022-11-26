package br.inatel.quotationmanagement.acceptance.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.QuotationController;
import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.controller.form.QuotationForm;
import br.inatel.quotationmanagement.controller.form.QuoteForm;
import br.inatel.quotationmanagement.exception.StockNotFoundException;
import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuotationRepository;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.service.StockService;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@AutoConfigureMockMvc
public class QuotationSteps {

    @Mock
    private StockService stockService;

    @Mock
    private QuotationRepository quotationRepository;

    @Mock
    private QuoteRepository quoteRepository;

    private QuotationController quotationController;

    @Autowired
    private MockMvc mockMvc;
    private ResultActions result;
    private JsonObject request;

    private QuotationForm quotationForm;
    private ResponseEntity<?> response;

    @Given("Some quotations registered in the database")
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        Quotation quotation = new Quotation("petr4");
        List<Quotation> quotations = new ArrayList<>();
        quotations.add(quotation);

        List<Quote> quotes = new ArrayList<>();
        quotes.add(new Quote(LocalDate.of(2021, 8, 12), new BigDecimal("14"), quotation));
        quotes.add(new Quote(LocalDate.of(2021, 8, 16), new BigDecimal("13"), quotation));

        Mockito.when(stockService.validate("petr4")).thenReturn(new StockDto("petr4", "Petrobras PN"));
        Mockito.when(quotationRepository.findAllByStockId("petr4")).thenReturn(Optional.of(quotations));

        Mockito.when(stockService.validate("petr0")).thenThrow(new StockNotFoundException("petr0"));

        quotationController = new QuotationController(stockService, quotationRepository, quoteRepository);
    }

    @And("a quotation with the stock id {string}")
    public void a_quotation_with_the_stock_id(String stockId) throws Exception {
        quotationForm = new QuotationForm();
        quotationForm.setStockId(stockId);
        quotationForm.setQuotes(new ArrayList<>());
    }

    @And("a {int} quotes to be added")
    public void a_quotes_to_be_added(int numberOfQuotes) {
        int hundredYears = 100 * 365;
        for (int i = 0; i < numberOfQuotes; i++) {
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

    @Given("A stock with stock id {string}")
    public void a_stock_with_stock_id(String stockId) {
        request = new JsonObject();
        request.add("stockId", stockId);
    }

    @Given("a quote with date {string} and price {string}")
    public void a_quote_with_date_and_price(String date, String price) {
        JsonObject quotes = new JsonObject();
        quotes.add(date, price);
        request.add("quotes", quotes);
    }

    @When("I send the request to store the quotation")
    public void i_send_the_request_to_store_the_quotation() throws Exception {
        result = mockMvc.perform(
                MockMvcRequestBuilders.post(new URI("/quotations")).content(request.asString()).contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the response http status code should be {int} and the error should be {string}")
    public void the_response_http_status_code_should_be_and_the_error_should_be(Integer httpStatus, String error) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().is(httpStatus)).andExpect(MockMvcResultMatchers.content().string(contains(error)));
    }

}
