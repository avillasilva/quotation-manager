package br.inatel.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.inatel.quotationmanagement.model.Quotation;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.repository.QuoteRepository;

public class QuotationForm {

    @NotNull
    @NotEmpty
    private String stockId;

    @Valid
    @NotNull
    @NotEmpty
    private List<QuoteForm> quotes;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public List<QuoteForm> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteForm> quotesForm) {
        this.quotes = quotesForm;
    }

    public Quotation convert(QuoteRepository quoteRepository) {
        Quotation quotation = new Quotation(stockId);
        quotes.forEach(quoteForm -> {
            Quote quote = new Quote(LocalDate.parse(quoteForm.getDate()), new BigDecimal(quoteForm.getPrice()), quotation);
            quotation.getQuotes().add(quote);
            quoteRepository.save(quote);
        });

        return quotation;
    }

    @JsonProperty("quotes")
    private void unpackNested(Map<String, String> quotesJson) {
        if (quotesJson == null) {
            quotes = null;
        } else {
            quotes = new ArrayList<QuoteForm>();

            Iterator<Map.Entry<String, String>> itr = quotesJson.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                quotes.add(new QuoteForm(entry.getKey(), entry.getValue()));
            }
        }
    }

}
