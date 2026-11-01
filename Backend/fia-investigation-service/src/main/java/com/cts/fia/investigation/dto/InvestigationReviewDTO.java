package com.cts.fia.investigation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationReviewDTO {
	 // Investigation
    private Integer investigationId;
    private String transactionId;
    private Integer riskScore;
    private String riskLevel;
    private String riskFactors;
    private String summary;
    private String recommendation;
    private String analystDecision;
    private String analystComments;

    // Customer
    private String customerId;
    private String customerName;

    // Transaction
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String currentCity;
    private String currentCountry;
    private Integer failedLoginAttempts;
    private Integer transactionsLast10Min;

    // Device
    private String deviceId;
    private String deviceName;
    private String deviceType;
}
