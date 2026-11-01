package com.cts.fia.customer.mapper;

import com.cts.fia.customer.dto.CustomerDTO;
import com.cts.fia.customer.entity.Customer;

public class CustomerMapper {

	// Entity -> DTO
	public static CustomerDTO toDto(Customer customer) {

		CustomerDTO dto = new CustomerDTO();

		dto.setCustomerId(customer.getCustomerId());
		dto.setCustomerName(customer.getCustomerName());
		dto.setUsualCity(customer.getUsualCity());
		dto.setUsualCountry(customer.getUsualCountry());
		dto.setAccountType(customer.getAccountType());

		return dto;
	}

	// DTO -> Entity
	public static Customer toEntity(CustomerDTO dto) {

		Customer customer = new Customer();

		customer.setCustomerId(dto.getCustomerId());
		customer.setCustomerName(dto.getCustomerName());
		customer.setUsualCity(dto.getUsualCity());
		customer.setUsualCountry(dto.getUsualCountry());
		customer.setAccountType(dto.getAccountType());

		return customer;
	}

}
