package br.inatel.quotationmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;

@DataJpaTest
@ActiveProfiles("test")
public class QuotationRepositoryTest {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void shoudLoadQuotatinByStockId() {
        Quotation quotation = new Quotation("petr4");

        em.persist(quotation);
        em.persist(new Quote(LocalDate.of(2021, 3, 7), new BigDecimal("14"), quotation));
        em.persist(new Quote(LocalDate.of(2021, 3, 12), new BigDecimal("17"), quotation));
        em.persist(new Quote(LocalDate.of(2021, 3, 17), new BigDecimal("18"), quotation));

        Optional<List<Quotation>> optional = quotationRepository.findAllByStockId("petr4");
        assertTrue(optional.isPresent());

        List<Quotation> quotations = optional.get();
        assertEquals(quotations.get(0).getStockId(), "petr4");
    }

}
