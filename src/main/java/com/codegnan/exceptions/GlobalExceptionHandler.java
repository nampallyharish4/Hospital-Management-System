package com.codegnan.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Centralized exception handling for HMS
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/*
	 * ============================ HMS BUSINESS EXCEPTIONS
	 * ============================
	 */
	@ExceptionHandler(InvalidDoctorIdException.class)
	public ResponseEntity<ApiError> handleDoctorNotFound(InvalidDoctorIdException ex, HttpServletRequest request) {
		log.warn("Doctor not found: {}", ex.getMessage());
		return buildError(HttpStatus.NOT_FOUND, "Doctor Not Found", ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InvalidPatientIdException.class)
	public ResponseEntity<ApiError> handlePatientNotFound(InvalidPatientIdException ex, HttpServletRequest request) {
		log.warn("Patient not found: {}", ex.getMessage());
		return buildError(HttpStatus.NOT_FOUND, "Patient Not Found", ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InvalidVisitIdException.class)
	public ResponseEntity<ApiError> handleVisitNotFound(InvalidVisitIdException ex, HttpServletRequest request) {
		log.warn("Visit not found: {}", ex.getMessage());
		return buildError(HttpStatus.NOT_FOUND, "Visit Not Found", ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<ApiError> handleInvalidDate(InvalidDateFormatException ex, HttpServletRequest request) {
		log.warn("Invalid date format", ex);
		return buildError(HttpStatus.BAD_REQUEST, "Invalid Date Format", ex.getMessage(), request.getRequestURI());
	}

	/*
	 * ============================ VALIDATION EXCEPTIONS
	 * ============================
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));
		log.warn("Validation failed: {}", errors);
		return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", errors, request.getRequestURI());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleMalformedJson(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		log.warn("Malformed JSON request", ex);
		return buildError(HttpStatus.BAD_REQUEST, "Invalid Input", "Malformed JSON request", request.getRequestURI());
	}

	/*
	 * ============================ CONCURRENCY (OPTIMISTIC LOCK)
	 * ============================
	 */
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ApiError> handleOptimisticLocking(ObjectOptimisticLockingFailureException ex,
			HttpServletRequest request) {
		log.warn("Optimistic locking failure", ex);
		return buildError(HttpStatus.CONFLICT, "Concurrent Update",
				"Record was updated by another user. Please refresh and retry.", request.getRequestURI());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
		log.warn("Resource not found: {}", ex.getMessage());
		return buildError(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage(), request.getRequestURI());
	}

	/*
	 * ============================ GENERIC EXCEPTION (LAST)
	 * ============================
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Unhandled exception occurred", ex);
		return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
				"An unexpected error occurred. Please contact support.", request.getRequestURI());
	}

	/*
	 * ============================ HELPER METHOD ============================
	 */
	private ResponseEntity<ApiError> buildError(HttpStatus status, String error, String message, String path) {
		ApiError apiError = new ApiError(LocalDateTime.now(), status.value(), error, message, path);
		return new ResponseEntity<>(apiError, status);
	}

}