package br.inatel.quotationmanagement.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Quotation {
	
	@Id @GeneratedValue(generator = "UUID")
	private UUID id;
	private String stockId;
	
	@OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL)
	private List<Quote> quotes;
	
	public Quotation() {}
	
	public Quotation(String stockId) {
		this.stockId = stockId;
		this.quotes = new ArrayList<Quote>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}
}
