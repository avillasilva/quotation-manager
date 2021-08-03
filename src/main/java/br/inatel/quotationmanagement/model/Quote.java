package br.inatel.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quote {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Quotation quotation; 
	
	private LocalDate date;
	private BigDecimal value;
	
	public Quote() {}

	public Quote(LocalDate date, BigDecimal value, Quotation quotation) {
		this.date = date;
		this.value = value;
		this.quotation = quotation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public Quotation getQuotation() {
		return quotation;
	}
	
	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}
}
