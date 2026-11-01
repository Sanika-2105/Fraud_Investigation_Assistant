package com.cts.fia.investigation.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.investigation.dto.TransactionDTO;

public interface TransactionService {
	List<TransactionDTO> getAllTransactions();
	
	TransactionDTO getTransactionById(String transactionId);
	void importTransactions(MultipartFile file);

}
