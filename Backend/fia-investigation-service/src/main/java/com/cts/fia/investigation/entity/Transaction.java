package com.cts.fia.investigation.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name ="transactions")
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
	
	@Id
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;

	@Column(name = "current_city")
	private String currentCity;

	@Column(name = "current_country")
	private String currentCountry;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "failed_login_attempts")
	private Integer failedLoginAttempts;

	@Column(name = "transactions_last_10_min")
	private Integer transactionsLast10Min;

}
