package com.codegnan.exceptions;

public class InvalidDateFormatException extends RuntimeException {
	public InvalidDateFormatException(Throwable cause) {
		super("Invalid date format. Expected format: dd-MM-yyyy", cause);
	}
}
