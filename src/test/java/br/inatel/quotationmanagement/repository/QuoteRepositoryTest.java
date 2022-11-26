package br.inatel.quotationmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;

@DataJpaTest
@ActiveProfiles("test")
public class QuoteRepositoryTest {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void shouldLoadAllByQuotationId() {
        em.persist(new Quotation("test"));

        List<Quotation> quotations = quotationRepository.findAllByStockId("test").get();
        assertEquals(1, quotations.size());

        Quotation quotation = quotations.get(0);
        em.persist(new Quote(LocalDate.of(2021, 3, 7), new BigDecimal("14"), quotation));
        em.persist(new Quote(LocalDate.of(2021, 3, 12), new BigDecimal("17"), quotation));
        em.persist(new Quote(LocalDate.of(2021, 3, 17), new BigDecimal("18"), quotation));

        List<Quote> quotes = quoteRepository.findAllByQuotationId(quotation.getId());
        assertEquals(3, quotes.size());
        assertEquals(new BigDecimal("14"), quotes.get(0).getValue());
    }

}
