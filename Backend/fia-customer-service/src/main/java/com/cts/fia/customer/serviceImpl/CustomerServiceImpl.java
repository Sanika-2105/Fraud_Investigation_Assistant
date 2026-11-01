package com.cts.fia.customer.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fia.customer.dto.CustomerDTO;
import com.cts.fia.customer.dto.DeviceDTO;
import com.cts.fia.customer.entity.Customer;
import com.cts.fia.customer.entity.Device;
import com.cts.fia.customer.exception.CustomerImportException;
import com.cts.fia.customer.exception.CustomerNotFoundException;
import com.cts.fia.customer.exception.DeviceImportException;
import com.cts.fia.customer.exception.DeviceNotFoundException;
import com.cts.fia.customer.mapper.CustomerMapper;
import com.cts.fia.customer.mapper.DeviceMapper;
import com.cts.fia.customer.repository.CustomerRepository;
import com.cts.fia.customer.repository.DeviceRepository;
import com.cts.fia.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepo;
	private final DeviceRepository deviceRepo;

	// Constructor Injection

	public CustomerServiceImpl(CustomerRepository customerRepo, DeviceRepository deviceRepo) {
		this.customerRepo = customerRepo;
		this.deviceRepo = deviceRepo;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepo.findAll().stream().map(CustomerMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(String customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(
						"Customer with ID " + customerId + " not found"));
		return CustomerMapper.toDto(customer);
	}

	@Override
	public List<DeviceDTO> getDevicesByCustomerId(String customerId) {

		List<Device> devices = deviceRepo.findByCustomerId(customerId);
		
		if(devices.isEmpty()) {
			throw new DeviceNotFoundException(
			"No devices found for customer ID: "
			+ customerId);
			}

		return devices.stream()
				      .map(DeviceMapper::toDto)
				      .collect(Collectors.toList());
	}

	@Override
	public List<DeviceDTO> getAllDevices() {
		return deviceRepo.findAll()
				.stream()
				.map(DeviceMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void importCustomers(MultipartFile file) {
		try (
		        BufferedReader reader =
		                new BufferedReader(
		                        new InputStreamReader(file.getInputStream()))
		    ) {

		        String line;

		        // Skip CSV header
		        reader.readLine();

		        while ((line = reader.readLine()) != null) {

		            String[] data = line.split(",");

		            Customer customer = new Customer();

		            customer.setCustomerId(data[0].trim());
		            customer.setCustomerName(data[1].trim());
		            customer.setUsualCity(data[2].trim());
		            customer.setUsualCountry(data[3].trim());
		            customer.setAccountType(data[4].trim());

		            if (!customerRepo.existsById(customer.getCustomerId())) {
		                customerRepo.save(customer);
		            } 
		        }
		        
		    } catch (Exception e) {
		        throw new CustomerImportException("Error importing customer CSV");
		    }
		
	}

	@Override
	public void importDevices(MultipartFile file) {
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

		            Device device = new Device();

		            device.setDeviceId(data[0].trim());
		            device.setCustomerId(data[1].trim());
		            device.setDeviceName(data[2].trim());
		            device.setDeviceType(data[3].trim());

		            if (!deviceRepo.existsById(device.getDeviceId())) {
		                deviceRepo.save(device);
		            }
		        }

		    } catch (Exception e) {
		        throw new DeviceImportException(
		                "Error importing device CSV"
		        );
		    }
		
	}

}
