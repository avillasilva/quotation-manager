package br.inatel.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuoteRepository;

public class QuotationForm {
	
	@NotNull @NotEmpty
	private String stockId;
	
	@NotNull @NotEmpty
	private Map<LocalDate, BigDecimal> quotes;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, BigDecimal> quotes) {
		this.quotes = quotes;
	}
	
	public Quotation convert(QuoteRepository quoteRepository) {
		Quotation quotation = new Quotation(stockId);
		quotes.forEach((date, value) -> {
			Quote quote = new Quote(date, value, quotation);
			quotation.getQuotes().add(quote);
			quoteRepository.save(quote);
		});
		return quotation;
	}
}
