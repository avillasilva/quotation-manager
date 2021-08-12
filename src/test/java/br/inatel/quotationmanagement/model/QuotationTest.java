package br.inatel.quotationmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QuotationTest {
	
	@Test
	public void shouldCreateQuoatation() {
		Quotation quotation = new Quotation("petr4");
		
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(LocalDate.of(2021, 8, 03), new BigDecimal("15"), quotation));
		quotes.add(new Quote(LocalDate.of(2021, 8, 04), new BigDecimal("17"), quotation));
		quotes.add(new Quote(LocalDate.of(2021, 8, 02), new BigDecimal("13"), quotation));
		
		assertEquals(quotes.size(), 3);
		assertEquals(quotes.get(0).getDate(), LocalDate.of(2021, 8, 03));
		assertEquals(quotes.get(1).getDate(), LocalDate.of(2021, 8, 04));
		assertEquals(quotes.get(2).getDate(), LocalDate.of(2021, 8, 02));
	}
}
