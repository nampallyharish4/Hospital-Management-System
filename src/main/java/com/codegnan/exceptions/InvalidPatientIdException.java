package com.codegnan.exceptions;

public class InvalidPatientIdException extends RuntimeException {
	public InvalidPatientIdException(String message) {
		super(message);
	}
}
