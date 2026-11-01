package com.cts.fia.investigation.mapper;

import com.cts.fia.investigation.dto.TransactionDTO;
import com.cts.fia.investigation.entity.Transaction;

public class TransactionMapper {
	
	// Entity -> DTO
	public static TransactionDTO toDto(Transaction transaction) {
	
	TransactionDTO dto = new TransactionDTO();

	dto.setTransactionId(transaction.getTransactionId());
	dto.setCustomerId(transaction.getCustomerId());
	dto.setAmount(transaction.getAmount());
	dto.setTransactionDate(transaction.getTransactionDate());
	dto.setCurrentCity(transaction.getCurrentCity());
	dto.setCurrentCountry(transaction.getCurrentCountry());
	dto.setDeviceId(transaction.getDeviceId());
	dto.setFailedLoginAttempts(transaction.getFailedLoginAttempts());
	dto.setTransactionsLast10Min(transaction.getTransactionsLast10Min());
	
	return dto;
	}

	// DTO -> Entity
	public static Transaction toEntity(TransactionDTO dto) {

	Transaction transaction = new Transaction();

	transaction.setTransactionId(dto.getTransactionId());
	transaction.setCustomerId(dto.getCustomerId());
	transaction.setAmount(dto.getAmount());
	transaction.setTransactionDate(dto.getTransactionDate());
	transaction.setCurrentCity(dto.getCurrentCity());
	transaction.setCurrentCountry(dto.getCurrentCountry());
	transaction.setDeviceId(dto.getDeviceId());
	transaction.setFailedLoginAttempts(dto.getFailedLoginAttempts());
	transaction.setTransactionsLast10Min(dto.getTransactionsLast10Min());

	return transaction;
	}

}
