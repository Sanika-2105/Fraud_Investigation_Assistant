package com.cts.fia.customer.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.customer.dto.CustomerDTO;
import com.cts.fia.customer.dto.DeviceDTO;

public interface CustomerService {
	
	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO getCustomerById(String customerId);
	
	List<DeviceDTO> getAllDevices();
	
	List<DeviceDTO> getDevicesByCustomerId(String customerId);
	
	void importCustomers(MultipartFile file);
	 void importDevices(MultipartFile file);

}
