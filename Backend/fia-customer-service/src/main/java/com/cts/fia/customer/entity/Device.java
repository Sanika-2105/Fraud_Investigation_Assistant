package com.cts.fia.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Device {
	
	@Id
	@Column(name = "device_id")
	private String deviceId;
	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "device_type")
	private String deviceType;

}
