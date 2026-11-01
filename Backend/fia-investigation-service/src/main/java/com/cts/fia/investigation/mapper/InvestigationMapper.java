package com.cts.fia.investigation.mapper;

import com.cts.fia.investigation.dto.InvestigationDTO;
import com.cts.fia.investigation.entity.Investigation;

public class InvestigationMapper {

	// Entity -> DTO
	public static InvestigationDTO toDto(Investigation investigation) {

		InvestigationDTO dto = new InvestigationDTO();

		dto.setInvestigationId(investigation.getInvestigationId());
		dto.setTransactionId(investigation.getTransactionId());
		dto.setRiskScore(investigation.getRiskScore());
		dto.setRiskLevel(investigation.getRiskLevel());
		dto.setRiskFactors(investigation.getRiskFactors());
		dto.setSummary(investigation.getSummary());
		dto.setRecommendation(investigation.getRecommendation());
		dto.setAnalystDecision(investigation.getAnalystDecision());
		dto.setAnalystComments(investigation.getAnalystComments());
		dto.setInvestigationDate(investigation.getInvestigationDate());

		return dto;
	}

	// DTO -> Entity
	public static Investigation toEntity(InvestigationDTO dto) {

		Investigation investigation = new Investigation();

		if (dto.getInvestigationId() != null) {
			investigation.setInvestigationId(dto.getInvestigationId());
		}

		investigation.setTransactionId(dto.getTransactionId());
		investigation.setRiskScore(dto.getRiskScore());
		investigation.setRiskLevel(dto.getRiskLevel());
		investigation.setRiskFactors(dto.getRiskFactors());
		investigation.setSummary(dto.getSummary());
		investigation.setRecommendation(dto.getRecommendation());
		investigation.setAnalystDecision(dto.getAnalystDecision());
		investigation.setAnalystComments(dto.getAnalystComments());
		investigation.setInvestigationDate(dto.getInvestigationDate());

		return investigation;
	}
}
