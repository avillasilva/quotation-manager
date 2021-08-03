package br.inatel.quotationmanagement.controller.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuoteValidator.class)
public @interface EqualDates {
	String message() default "Two or more quotes for the same date";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
