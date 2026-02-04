package com.codegnan.exceptions;

import java.time.LocalDateTime;

public class ApiError {
	private final LocalDateTime timestamp; // when the error occurred
	private final int status; // HTTP status code (e.g. 404)
	private final String error; // error type/category
	private final String message; // user-friendly message
	private final String path; // API path

	public ApiError(LocalDateTime timestamp, int status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}
}