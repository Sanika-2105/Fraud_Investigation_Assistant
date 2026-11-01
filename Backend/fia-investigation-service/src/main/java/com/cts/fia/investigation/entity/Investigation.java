package com.cts.fia.investigation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "investigations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investigation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "investigation_id")
	private Integer investigationId;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "risk_score")
	private Integer riskScore;

	@Column(name = "risk_level")
	private String riskLevel;

	@Column(name = "risk_factors")
	private String riskFactors;

	@Column(name = "summary")
	private String summary;

	@Column(name = "recommendation")
	private String recommendation;

	@Column(name = "analyst_decision")
	private String analystDecision;

	@Column(name = "analyst_comments")
	private String analystComments;

	@Column(name = "investigation_date")
	private LocalDateTime investigationDate;

}
