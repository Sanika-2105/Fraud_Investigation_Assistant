package com.cts.fia.customer.exceptionImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.fia.customer.exception.CustomerImportException;
import com.cts.fia.customer.exception.CustomerNotFoundException;
import com.cts.fia.customer.exception.DeviceImportException;
import com.cts.fia.customer.exception.DeviceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCustomerNotFound(CustomerNotFoundException ex) {

		Map<String, Object> errorResponse = new HashMap<>();

		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		errorResponse.put("error", "Customer Not Found");
		errorResponse.put("message", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DeviceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleDeviceNotFound(DeviceNotFoundException ex) {

		Map<String, Object> errorResponse = new HashMap<>();

		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		errorResponse.put("error", "Device Not Found");
		errorResponse.put("message", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CustomerImportException.class)
	public ResponseEntity<Map<String, Object>> handleCustomerImportException(
	        CustomerImportException ex) {

	    Map<String, Object> errorResponse = new HashMap<>();

	    errorResponse.put("timestamp", LocalDateTime.now());
	    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
	    errorResponse.put("error", "Customer Import Failed");
	    errorResponse.put("message", ex.getMessage());

	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DeviceImportException.class)
	public ResponseEntity<Map<String, Object>> handleDeviceImportException(
	        DeviceImportException ex) {

	    Map<String, Object> errorResponse = new HashMap<>();

	    errorResponse.put("timestamp", LocalDateTime.now());
	    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
	    errorResponse.put("error", "Device Import Failed");
	    errorResponse.put("message", ex.getMessage());

	    return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.BAD_REQUEST
	    );
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
	}

	private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {

		Map<String, Object> body = new HashMap<>();

		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);

		return new ResponseEntity<>(body, status);
	}

}
