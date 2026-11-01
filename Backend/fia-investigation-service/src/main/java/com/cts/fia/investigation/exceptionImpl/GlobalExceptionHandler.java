package com.cts.fia.investigation.exceptionImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.fia.investigation.exception.InvestigationNotFoundException;
import com.cts.fia.investigation.exception.TransactionImportException;
import com.cts.fia.investigation.exception.TransactionImportException;
import com.cts.fia.investigation.exception.TransactionNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleTransactionNotFound(TransactionNotFoundException ex) {

		Map<String, Object> errorResponse = new HashMap<>();

		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		errorResponse.put("error", "Transaction Not Found");
		errorResponse.put("message", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvestigationNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleInvestigationNotFound(InvestigationNotFoundException ex) {

		Map<String, Object> errorResponse = new HashMap<>();

		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		errorResponse.put("error", "Investigation Not Found");
		errorResponse.put("message", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

		Map<String, Object> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(TransactionImportException.class)
	public ResponseEntity<Map<String, Object>>
	handleTransactionImportException(
	        TransactionImportException ex) {

	    Map<String, Object> errorResponse =
	        new HashMap<>();

	    errorResponse.put(
	        "timestamp",
	        LocalDateTime.now()
	    );

	    errorResponse.put(
	        "status",
	        HttpStatus.BAD_REQUEST.value()
	    );

	    errorResponse.put(
	        "error",
	        "Transaction Import Failed"
	    );

	    errorResponse.put(
	        "message",
	        ex.getMessage()
	    );

	    return new ResponseEntity<>(
	        errorResponse,
	        HttpStatus.BAD_REQUEST
	    );
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {

		Map<String, Object> error = new HashMap<>();

		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", "Validation Error");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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
