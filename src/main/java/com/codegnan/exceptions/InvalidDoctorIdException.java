package com.codegnan.exceptions;

public class InvalidDoctorIdException extends RuntimeException {
	public InvalidDoctorIdException(String message) {
		super(message);
	}
}
