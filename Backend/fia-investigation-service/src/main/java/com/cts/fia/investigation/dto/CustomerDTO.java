package com.cts.fia.investigation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	private String customerId;

	private String customerName;

	private String usualCity;

	private String usualCountry;

	private String accountType;

}
