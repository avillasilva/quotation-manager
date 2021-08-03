package br.inatel.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class QuoteTest {
	
	@Test
	public void shouldCreateAQuote() {
		Quotation quotation = new Quotation("petr5");
		Quote quote = new Quote(LocalDate.of(2021, 7, 8), new BigDecimal("17"), quotation);
		
		Assert.assertEquals(quote.getDate(), LocalDate.of(2021, 7, 8));
		Assert.assertEquals(quote.getValue(), new BigDecimal("17"));
		Assert.assertEquals("petr4", quote.getQuotation().getId());
	}
}
