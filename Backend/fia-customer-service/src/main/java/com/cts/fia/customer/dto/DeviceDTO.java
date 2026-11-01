package com.cts.fia.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeviceDTO {

	private String deviceId;
	
	private String customerId;
	
	private String deviceName;
	
	private String deviceType;
}
