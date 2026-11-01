package com.cts.fia.investigation.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cts.fia.investigation.dto.CustomerDTO;
import com.cts.fia.investigation.dto.DecisionDTO;
import com.cts.fia.investigation.dto.DeviceDTO;
import com.cts.fia.investigation.dto.InvestigationDTO;
import com.cts.fia.investigation.dto.InvestigationReviewDTO;
import com.cts.fia.investigation.entity.Investigation;
import com.cts.fia.investigation.entity.Transaction;
import com.cts.fia.investigation.exception.InvestigationNotFoundException;
import com.cts.fia.investigation.exception.TransactionNotFoundException;
import com.cts.fia.investigation.feign.CustomerFeignClient;
import com.cts.fia.investigation.mapper.InvestigationMapper;
import com.cts.fia.investigation.repository.InvestigationRepository;
import com.cts.fia.investigation.repository.TransactionRepository;
import com.cts.fia.investigation.service.InvestigationService;

@Service
public class InvestigationServiceImpl implements InvestigationService {

	private final InvestigationRepository investigationRepo;
	private final TransactionRepository transactionRepo;
	private final CustomerFeignClient customerFeignClient;

	// Constructor Injection
	public InvestigationServiceImpl(InvestigationRepository investigationRepo, TransactionRepository transactionRepo,
			CustomerFeignClient customerFeignClient) {

		this.investigationRepo = investigationRepo;
		this.transactionRepo = transactionRepo;
		this.customerFeignClient = customerFeignClient;
	}

	@Override
	public List<InvestigationDTO> getAllInvestigations() {
		return investigationRepo.findAll().stream().map(InvestigationMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public InvestigationDTO getInvestigationById(Integer investigationId) {
		Investigation investigation = investigationRepo.findById(investigationId).orElseThrow(
				() -> new InvestigationNotFoundException("Investigation with ID " + investigationId + " not found"));

		return InvestigationMapper.toDto(investigation);
	}

	@Override
	public InvestigationDTO analyzeTransaction(String transactionId) {
		if (investigationRepo.existsByTransactionId(
	            transactionId)) {

	        throw new IllegalArgumentException(
	            "Investigation already exists for transaction "
	            + transactionId
	        );
	    }
		Transaction transaction = transactionRepo.findById(transactionId).orElseThrow(
				
				() -> new TransactionNotFoundException("Transaction with ID " + transactionId + " not found"));

		CustomerDTO customer = customerFeignClient.getCustomerById(transaction.getCustomerId());

		List<DeviceDTO> devices = customerFeignClient.getDevicesByCustomerId(transaction.getCustomerId());

		int riskScore = 0;

		StringBuilder riskFactors = new StringBuilder();

		// Rule 1 : Unknown Device (+20)

		boolean knownDevice = devices.stream()
				.anyMatch(device -> device.getDeviceId().equalsIgnoreCase(transaction.getDeviceId()));

		if (!knownDevice) {

			riskScore += 20;

			riskFactors.append("Unknown Device");
		}

		// Rule 2 : Unusual City (+20)

		if (!transaction.getCurrentCity().equalsIgnoreCase(customer.getUsualCity())) {

			riskScore += 20;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("Unusual City");
		}

		// Rule 3 : Foreign Transaction (+30)

		if (!transaction.getCurrentCountry().equalsIgnoreCase(customer.getUsualCountry())) {

			riskScore += 30;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("Foreign Transaction");
		}

		// Rule 4 : Amount Analysis

		Double averageAmount = transactionRepo.getAverageTransactionAmountByCustomerId(transaction.getCustomerId());

		double currentAmount = transaction.getAmount().doubleValue();

		if (averageAmount != null) {

			if (currentAmount > averageAmount * 4) {

				riskScore += 40;

				if (riskFactors.length() > 0) {
					riskFactors.append(", ");
				}

				riskFactors.append("Unusual Transaction Amount");
			}

			else if (currentAmount > averageAmount * 2) {

				riskScore += 20;

				if (riskFactors.length() > 0) {
					riskFactors.append(", ");
				}

				riskFactors.append("Unusual Transaction Amount");
			}

			else if (currentAmount > averageAmount * 1.5) {

				riskScore += 10;

				if (riskFactors.length() > 0) {
					riskFactors.append(", ");
				}

				riskFactors.append("Unusual Transaction Amount");
			}
		}

		System.out.println("Average Amount : " + averageAmount);

		System.out.println("Current Amount : " + currentAmount);

		// Rule 5 : Failed Login Attempts

		if (transaction.getFailedLoginAttempts() > 5) {

			riskScore += 20;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("Multiple Failed Login Attempts");
		} else if (transaction.getFailedLoginAttempts() >= 3) {

			riskScore += 10;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("Multiple Failed Login Attempts");
		}

		// Rule 6 : High Transaction Frequency

		if (transaction.getTransactionsLast10Min() > 10) {

			riskScore += 20;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("High Transaction Frequency");
		} else if (transaction.getTransactionsLast10Min() >= 4) {

			riskScore += 10;

			if (riskFactors.length() > 0) {
				riskFactors.append(", ");
			}

			riskFactors.append("High Transaction Frequency");
		}

		// Cap Risk Score

		riskScore = Math.min(riskScore, 100);

		// Determine Risk Level

		String riskLevel;

		if (riskScore >= 70) {
			riskLevel = "HIGH";
		} else if (riskScore >= 30) {
			riskLevel = "MEDIUM";
		} else {
			riskLevel = "LOW";
		}

		// Generate Summary

		String summary = "Risk factors identified: " + riskFactors.toString();

		// Generate Recommendation

		String recommendation;

		if ("HIGH".equals(riskLevel)) {

			recommendation = "Manual Review Recommended";

		} else if ("MEDIUM".equals(riskLevel)) {

			recommendation = "Monitor Transaction";

		} else {

			recommendation = "Transaction Appears Safe";
		}

		// Create Investigation Object

		Investigation investigation = new Investigation();

		investigation.setTransactionId(transaction.getTransactionId());

		investigation.setRiskScore(riskScore);

		investigation.setRiskLevel(riskLevel);

		investigation.setRiskFactors(riskFactors.toString());

		investigation.setSummary(summary);

		investigation.setRecommendation(recommendation);

		investigation.setAnalystDecision(null);

		investigation.setAnalystComments(null);

		investigation.setInvestigationDate(LocalDateTime.now());

		// Save Investigation

		Investigation savedInvestigation = investigationRepo.save(investigation);

		// Debug Prints (optional)

		System.out.println("Customer : " + customer);
		System.out.println("Devices : " + devices);
		System.out.println("Risk Score : " + riskScore);
		System.out.println("Risk Level : " + riskLevel);
		System.out.println("Risk Factors : " + riskFactors);
		System.out.println("Recommendation : " + recommendation);

		// Return DTO

		return InvestigationMapper.toDto(savedInvestigation);

//		System.out.println("Customer : " + customer);
//		System.out.println("Devices : " + devices);
//		System.out.println("Risk Score : " + riskScore);
//		System.out.println("Risk Factors : " + riskFactors);
//
//		return null;
	}

	@Override
	public InvestigationDTO updateDecision(Integer investigationId, DecisionDTO decisionDTO) {
		Investigation investigation = investigationRepo.findById(investigationId).orElseThrow(
				() -> new InvestigationNotFoundException("Investigation with ID " + investigationId + " not found"));

		String decision = decisionDTO.getDecision().toUpperCase();

		// Decision Validation

		if (!decision.equals("APPROVED") && !decision.equals("BLOCKED") && !decision.equals("ESCALATED")) {

			throw new IllegalArgumentException("Decision must be APPROVED, BLOCKED or ESCALATED");
		}

		investigation.setAnalystDecision(decision);

		investigation.setAnalystComments(decisionDTO.getComments());

		Investigation updatedInvestigation = investigationRepo.save(investigation);

		return InvestigationMapper.toDto(updatedInvestigation);
	}
	@Override
	public InvestigationReviewDTO getReviewDetails(
	        Integer investigationId) {

	    Investigation investigation =
	            investigationRepo.findById(investigationId)
	            .orElseThrow(() ->
	                    new InvestigationNotFoundException(
	                            "Investigation with ID "
	                                    + investigationId
	                                    + " not found"));

	    Transaction transaction =
	            transactionRepo.findById(
	                    investigation.getTransactionId())
	            .orElseThrow(() ->
	                    new TransactionNotFoundException(
	                            "Transaction not found"));

	    CustomerDTO customer =
	            customerFeignClient.getCustomerById(
	                    transaction.getCustomerId());

	    List<DeviceDTO> devices =
	            customerFeignClient.getDevicesByCustomerId(
	                    transaction.getCustomerId());
	    System.out.println("Transaction Device ID : "
	            + transaction.getDeviceId());

	    System.out.println("Devices : " + devices);
	    DeviceDTO matchedDevice = devices.stream()
	            .filter(device ->
	                    device.getDeviceId()
	                    .equalsIgnoreCase(
	                            transaction.getDeviceId()))
	            .findFirst()
	            .orElse(null);

	    InvestigationReviewDTO review =
	            new InvestigationReviewDTO();

	    // Investigation Details
	    review.setInvestigationId(
	            investigation.getInvestigationId());

	    review.setTransactionId(
	            investigation.getTransactionId());

	    review.setRiskScore(
	            investigation.getRiskScore());

	    review.setRiskLevel(
	            investigation.getRiskLevel());

	    review.setRiskFactors(
	            investigation.getRiskFactors());

	    review.setSummary(
	            investigation.getSummary());

	    review.setRecommendation(
	            investigation.getRecommendation());

	    review.setAnalystDecision(
	            investigation.getAnalystDecision());

	    review.setAnalystComments(
	            investigation.getAnalystComments());

	    // Customer Details
	    review.setCustomerId(
	            customer.getCustomerId());

	    review.setCustomerName(
	            customer.getCustomerName());

	    // Transaction Details
	    review.setAmount(
	            transaction.getAmount());

	    review.setTransactionDate(
	            transaction.getTransactionDate());

	    review.setCurrentCity(
	            transaction.getCurrentCity());

	    review.setCurrentCountry(
	            transaction.getCurrentCountry());

	    review.setFailedLoginAttempts(
	            transaction.getFailedLoginAttempts());

	    review.setTransactionsLast10Min(
	            transaction.getTransactionsLast10Min());

	    // Device Details
	    review.setDeviceId(
	            transaction.getDeviceId());

	    if (matchedDevice != null) {

	        review.setDeviceName(
	                matchedDevice.getDeviceName());

	        review.setDeviceType(
	                matchedDevice.getDeviceType());

	    } else {

	        review.setDeviceName(
	                "Unknown Device");

	        review.setDeviceType(
	                "Not Registered");
	    }
	    return review;
	}
}
