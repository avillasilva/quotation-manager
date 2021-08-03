package br.inatel.quotationmanagement.controller.form;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuoteValidator implements ConstraintValidator<EqualDates, QuotationForm> {
	
	@Override
	public boolean isValid(QuotationForm form, ConstraintValidatorContext context) {
		return true;
	}
}
