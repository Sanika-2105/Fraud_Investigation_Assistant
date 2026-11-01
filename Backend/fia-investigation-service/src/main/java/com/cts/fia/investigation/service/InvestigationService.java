package com.cts.fia.investigation.service;

import java.util.List;

import com.cts.fia.investigation.dto.DecisionDTO;
import com.cts.fia.investigation.dto.InvestigationDTO;
import com.cts.fia.investigation.dto.InvestigationReviewDTO;

public interface InvestigationService {

	List<InvestigationDTO> getAllInvestigations();

	InvestigationDTO getInvestigationById(Integer investigationId);

	InvestigationDTO analyzeTransaction(String transactionId);

	InvestigationDTO updateDecision(Integer investigationId, DecisionDTO decisionDTO);

	InvestigationReviewDTO getReviewDetails(Integer investigationId);
}
