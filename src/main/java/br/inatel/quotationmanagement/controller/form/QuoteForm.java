package br.inatel.quotationmanagement.controller.form;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

public class QuoteForm {
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid format. The date must match YYYY-MM-DD.")
	private String date;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^([0-9]*[.])?[0-9]+$", message = "Invalid format. The price must match the 00.00.")
	private String price;
	
	public QuoteForm(String date, String price) {
		this.date = date;
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuoteForm other = (QuoteForm) obj;
		return Objects.equals(date, other.date) && Objects.equals(price, other.price);
	}
}
