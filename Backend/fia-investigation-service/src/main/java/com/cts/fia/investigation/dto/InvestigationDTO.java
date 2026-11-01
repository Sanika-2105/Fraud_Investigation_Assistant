package com.cts.fia.investigation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationDTO {
	
	private Integer investigationId;
	
	private String transactionId;
	
	private Integer riskScore;
	
	private String riskLevel;
	
	private String riskFactors;
	
	private String summary;
	
	private String recommendation;
	
	private String analystDecision;
	
	private String analystComments;
	
	private LocalDateTime investigationDate;

}
