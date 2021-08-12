package br.inatel.quotationmanagement.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
	
	public String id;
	public String description;
	
	public Stock() {}
	
	public Stock(String stockId) {
		this.id = stockId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
