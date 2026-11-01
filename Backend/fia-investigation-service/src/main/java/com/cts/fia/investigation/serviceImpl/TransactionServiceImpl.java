package com.cts.fia.investigation.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.investigation.dto.TransactionDTO;
import com.cts.fia.investigation.entity.Transaction;
import com.cts.fia.investigation.exception.TransactionNotFoundException;
import com.cts.fia.investigation.mapper.TransactionMapper;
import com.cts.fia.investigation.repository.TransactionRepository;
import com.cts.fia.investigation.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepo;

	// Constructor Injection
	public TransactionServiceImpl(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}

	@Override
	public List<TransactionDTO> getAllTransactions() {
		return transactionRepo.findAll().stream().map(TransactionMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public TransactionDTO getTransactionById(String transactionId) {
		Transaction transaction = transactionRepo.findById(transactionId)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + transactionId + " not found"));

		return TransactionMapper.toDto(transaction);
	}

	@Override
	public void importTransactions(MultipartFile file) {
		try (
		        BufferedReader reader =
		            new BufferedReader(
		                new InputStreamReader(file.getInputStream()))
		    ) {

		        String line;

		        // Skip CSV Header
		        reader.readLine();

		        while ((line = reader.readLine()) != null) {

		            String[] data = line.split(",");

		            Transaction transaction = new Transaction();

		            transaction.setTransactionId(data[0].trim());

		            transaction.setCustomerId(data[1].trim());

		            transaction.setAmount(
		                new BigDecimal(data[2].trim())
		            );

		            transaction.setTransactionDate(
		                LocalDateTime.parse(data[3].trim())
		            );

		            transaction.setCurrentCity(data[4].trim());

		            transaction.setCurrentCountry(data[5].trim());

		            transaction.setDeviceId(data[6].trim());

		            transaction.setFailedLoginAttempts(
		                Integer.parseInt(data[7].trim())
		            );

		            transaction.setTransactionsLast10Min(
		                Integer.parseInt(data[8].trim())
		            );

		            if (!transactionRepo.existsById(
		                    transaction.getTransactionId())) {

		                transactionRepo.save(transaction);
		            }
		        }

		    } catch (Exception e) {
		        throw new RuntimeException(
		            "Failed to import transactions"
		        );
		    }
		
	}

}
