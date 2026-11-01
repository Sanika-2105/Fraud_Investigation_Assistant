package com.cts.fia.investigation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	
	private String transactionId;
	
	private String customerId;

	private BigDecimal amount;

	private LocalDateTime transactionDate;

	private String currentCity;

	private String currentCountry;

	private String deviceId;

	private Integer failedLoginAttempts;

	private Integer transactionsLast10Min;

}
