package com.cts.fia.investigation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.fia.investigation.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query("""
			SELECT AVG(t.amount)
			FROM Transaction t
			WHERE t.customerId = :customerId
			""")
	
	Double getAverageTransactionAmountByCustomerId(@Param("customerId") String customerId);
}
