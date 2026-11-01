package com.cts.fia.investigation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.investigation.dto.TransactionDTO;
import com.cts.fia.investigation.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction API", description = "Operations related to transaction management")
public class TransactionController {

	private final TransactionService transactionService;

	// Constructor Injection
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Operation(summary = "Get all transactions", description = "Fetch all transactions from the system")
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {

		return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
	}

	@Operation(summary = "Get transaction by ID", description = "Fetch transaction details using transaction ID")
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String transactionId) {

		return new ResponseEntity<>(transactionService.getTransactionById(transactionId), HttpStatus.OK);
	}
	@Operation(
		    summary = "Import transactions from CSV",
		    description = "Upload a CSV file containing transaction records and import them into the system."
		)
	@PostMapping("/import")
	public ResponseEntity<String> importTransactions(
	        @RequestParam("file") MultipartFile file) {

	    transactionService.importTransactions(file);

	    return ResponseEntity.ok(
	        "Transactions imported successfully"
	    );
	}
}
