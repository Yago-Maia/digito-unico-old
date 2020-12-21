package com.desafio.inter.errorHandler;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long userId) {
        super(String.format("User with id %s not found.", userId));
    }

}