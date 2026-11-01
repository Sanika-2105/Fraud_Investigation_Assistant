package com.cts.fia.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "usual_city")
	private String usualCity;

	@Column(name = "usual_country")
	private String usualCountry;

	@Column(name = "account_type")
	private String accountType;

}
