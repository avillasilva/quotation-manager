package br.inatel.quotationmanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class QuotationRepositoryTest {
	
	@Autowired
	private QuotationRepository quotationRepository;
	
	@Before
	public void setup() {
		Quotation quotation = new Quotation("petr4");
		
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(LocalDate.of(2021, 3, 7), new BigDecimal("14"), quotation));
		quotes.add(new Quote(LocalDate.of(2021, 3, 12), new BigDecimal("17"), quotation));
		quotes.add(new Quote(LocalDate.of(2021, 3, 17), new BigDecimal("18"), quotation));
		quotation.setQuotes(quotes);
		
		quotationRepository.save(quotation);
	}
	
	@Test
	public void shoudLoadQuotatinByStockId() {
		Optional<List<Quotation>> optional = quotationRepository.findAllByStockId("petr4");
		
		Assert.assertTrue(optional.isPresent());
		List<Quotation> quotations = optional.get();
		
		Assert.assertEquals(quotations.get(0).getStockId(), "petr4");
	}
}
