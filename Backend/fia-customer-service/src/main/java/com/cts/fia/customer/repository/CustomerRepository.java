package com.cts.fia.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fia.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
