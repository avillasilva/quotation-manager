package br.inatel.quotationmanagement.controller.form;

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
	private Map<LocalDate, Long> quotes;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, Long> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, Long> quotes) {
		this.quotes = quotes;
	}
	
	public Quotation convert(QuoteRepository quoteRepository) {
		Quotation quotation = new Quotation(stockId);
		quotes.forEach((date, value) -> quoteRepository.save(new Quote(date, value, quotation)));
		return quotation;
	}
}
