package br.inatel.quotationmanagement.controller.dto;

public class StockDto {
	
	private String stockId;
	private String description;
	
	public String getStockId() {
		return stockId;
	}
	
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
