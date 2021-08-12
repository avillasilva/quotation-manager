package br.inatel.quotationmanagement.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.inatel.quotationmanagement.util.exceptions.IncorrectDateFormatException;
import br.inatel.quotationmanagement.util.exceptions.IncorrectPriceFormatException;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorFormDto> handle(MethodArgumentNotValidException exception) {
		List<ErrorFormDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorFormDto error = new ErrorFormDto(e.getField(), msg);
			dto.add(error);
		});
		
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IncorrectDateFormatException.class)
	public ErrorFormDto handle(IncorrectDateFormatException exception) {
		return new ErrorFormDto("quotes", exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IncorrectPriceFormatException.class)
	public ErrorFormDto handle(IncorrectPriceFormatException exception) {
		return new ErrorFormDto("quotes", exception.getMessage());
	}
}
