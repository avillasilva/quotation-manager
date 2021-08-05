package br.inatel.quotationmanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
public class QuoteRepositoryTest {
	
	@Autowired
	private QuotationRepository quotationRepository;
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	@Before
	public void setup() {
		Quotation quotation = new Quotation("petr4");
		quotationRepository.save(quotation);
		
		quoteRepository.save(new Quote(LocalDate.of(2021, 3, 7), new BigDecimal("14"), quotation));
		quoteRepository.save(new Quote(LocalDate.of(2021, 3, 12), new BigDecimal("17"), quotation));
		quoteRepository.save(new Quote(LocalDate.of(2021, 3, 17), new BigDecimal("18"), quotation));
	}
	
	@Test
	public void shoudLoadAllByQuotationId() {
		List<Quote> quotes = quoteRepository.findAllByQuotationId(Long.parseLong("3"));
		
		Assert.assertEquals(quotes.size(), 3);
		Assert.assertEquals(quotes.get(0).getValue(), new BigDecimal("14"));
	}

}
