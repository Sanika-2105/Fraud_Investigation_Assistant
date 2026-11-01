package com.cts.fia.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.customer.dto.CustomerDTO;
import com.cts.fia.customer.dto.DeviceDTO;
import com.cts.fia.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customers")
@Tag(
name = "Customer API",
description = "Operations related to Customer and Device Information"
)
public class CustomerController {

	private final CustomerService customerService;

	// Constructor Injection
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
    
	@Operation(
		    summary = "Get all customers",
		    description = "Fetch all customer records available in the system"
		)
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@Operation(
		    summary = "Get customer by ID",
		    description = "Fetch customer details using the customer ID"
		)
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String customerId) {

		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}

	@Operation(
		    summary = "Get devices by customer ID",
		    description = "Fetch all registered devices associated with a specific customer"
		)
	@GetMapping("/{customerId}/devices")
	public ResponseEntity<List<DeviceDTO>> getDevicesByCustomerId(@PathVariable String customerId) {

		return new ResponseEntity<>(customerService.getDevicesByCustomerId(customerId), HttpStatus.OK);
	}

	@Operation(
		    summary = "Get all devices",
		    description = "Fetch all device records available in the system"
		)
	@GetMapping("/devices")
	public ResponseEntity<List<DeviceDTO>> getAllDevices() {

		return new ResponseEntity<>(customerService.getAllDevices(), HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Import customers from CSV",
		    description = "Upload a CSV file containing customer records and import them into the system"
		)
	@PostMapping("/import")
	public ResponseEntity<String> importCustomers(
	        @RequestParam("file") MultipartFile file) {

	    customerService.importCustomers(file);

	    return ResponseEntity.ok("Customers imported successfully");
	}
	
	@Operation(
		    summary = "Import devices from CSV",
		    description = "Upload a CSV file containing device records and import them into the system"
		)
	@PostMapping("/devices/import")
	public ResponseEntity<String> importDevices(
	        @RequestParam("file") MultipartFile file) {

	    customerService.importDevices(file);

	    return ResponseEntity.ok(
	            "Devices imported successfully"
	    );
	}

}
