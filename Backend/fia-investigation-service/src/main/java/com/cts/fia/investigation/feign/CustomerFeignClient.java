package com.cts.fia.investigation.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.fia.investigation.dto.CustomerDTO;
import com.cts.fia.investigation.dto.DeviceDTO;

@FeignClient(name = "fia-customer-service")
public interface CustomerFeignClient {

	@GetMapping("/customers/{customerId}")
	CustomerDTO getCustomerById(@PathVariable("customerId") String customerId);

	@GetMapping("/customers/{customerId}/devices")
	List<DeviceDTO> getDevicesByCustomerId(@PathVariable("customerId") String customerId);

}
