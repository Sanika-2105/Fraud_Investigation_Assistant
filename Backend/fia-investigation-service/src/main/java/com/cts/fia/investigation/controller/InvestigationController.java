package com.cts.fia.investigation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fia.investigation.dto.DecisionDTO;
import com.cts.fia.investigation.dto.InvestigationDTO;
import com.cts.fia.investigation.dto.InvestigationReviewDTO;
import com.cts.fia.investigation.service.InvestigationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/investigations")
@Tag(name = "Investigation API", description = "Operations related to fraud investigation and risk analysis")
public class InvestigationController {
	private final InvestigationService investigationService;

	// Constructor Injection
	public InvestigationController(InvestigationService investigationService) {

		this.investigationService = investigationService;
	}

	@Operation(summary = "Get all investigations", description = "Fetch all investigations from the system")
	@GetMapping
	public ResponseEntity<List<InvestigationDTO>> getAllInvestigations() {

		return new ResponseEntity<>(investigationService.getAllInvestigations(), HttpStatus.OK);
	}

	@Operation(summary = "Get investigation by ID", description = "Fetch investigation details using investigation ID")
	@GetMapping("/{investigationId}")
	public ResponseEntity<InvestigationDTO> getInvestigationById(@PathVariable Integer investigationId) {

		return new ResponseEntity<>(investigationService.getInvestigationById(investigationId), HttpStatus.OK);
	}
	@Operation(
		    summary = "Analyze transaction",
		    description = "Analyze a transaction using predefined fraud detection rules and generate an investigation with risk score, risk level, and recommendations"
		)
	@PostMapping("/analyze/{transactionId}")
	public ResponseEntity<InvestigationDTO> analyzeTransaction(@PathVariable String transactionId) {

		return new ResponseEntity<>(investigationService.analyzeTransaction(transactionId), HttpStatus.OK);
	}
	@Operation(
		    summary = "Update analyst decision",
		    description = "Allow an analyst to review an investigation and submit a decision such as APPROVED, BLOCKED, or ESCALATED along with comments"
		)
	@PutMapping("/{investigationId}/decision")
	public ResponseEntity<InvestigationDTO> updateDecision(@PathVariable Integer investigationId,
			@Valid @RequestBody DecisionDTO decisionDTO) {

		return new ResponseEntity<>(investigationService.updateDecision(investigationId, decisionDTO), HttpStatus.OK);
	}
	@Operation(
		    summary = "Get investigation review details",
		    description = "Fetch complete investigation review information including customer details, transaction details, device details, risk factors, risk score, recommendation, and analyst comments"
		)
	@GetMapping("/{investigationId}/review")
	public ResponseEntity<InvestigationReviewDTO> getReviewDetails(
	        @PathVariable Integer investigationId) {

	    return new ResponseEntity<>(
	            investigationService.getReviewDetails(investigationId),
	            HttpStatus.OK);
	}
}
