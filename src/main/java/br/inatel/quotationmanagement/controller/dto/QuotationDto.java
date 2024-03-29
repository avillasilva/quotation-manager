package br.inatel.quotationmanagement.controller.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Quotation;

public class QuotationDto {

    private String id;
    private String stockId;
    private Map<LocalDate, String> quotes;

    public QuotationDto(Quotation quotation) {
        this.id = quotation.getUuid();
        this.stockId = quotation.getStockId();
        this.quotes = new HashMap<LocalDate, String>();
        quotation.getQuotes().forEach(quote -> this.quotes.put(quote.getDate(), quote.getValue().toString()));
    }

    public String getId() {
        return id;
    }

    public void setId(String uuid) {
        this.id = uuid;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Map<LocalDate, String> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<LocalDate, String> quotes) {
        this.quotes = quotes;
    }

    public static List<QuotationDto> convert(List<Quotation> quotations) {
        return quotations.stream().map(QuotationDto::new).collect(Collectors.toList());
    }

}
