package com.desafio.inter.errorHandler;

public class FieldValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FieldValidationException(String message) {
        super(message);
    }

}