package com.cts.fia.investigation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fia.investigation.entity.Investigation;

@Repository
public interface InvestigationRepository extends JpaRepository<Investigation, Integer> {
	boolean existsByTransactionId(String transactionId);

}
