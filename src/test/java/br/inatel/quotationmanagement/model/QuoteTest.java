package br.inatel.quotationmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class QuoteTest {

    @Test
    public void shouldCreateAQuote() {
        Quotation quotation = new Quotation("petr5");
        Quote quote = new Quote(LocalDate.of(2021, 7, 8), new BigDecimal("17"), quotation);

        assertEquals(LocalDate.of(2021, 7, 8), quote.getDate());
        assertEquals(new BigDecimal("17"), quote.getValue());
        assertEquals("petr5", quote.getQuotation().getStockId());
    }

}
