package br.inatel.quotationmanagement.util.exceptions;

public class IncorrectPriceFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IncorrectPriceFormatException(String message) {
		super(message);
	}
	
}
