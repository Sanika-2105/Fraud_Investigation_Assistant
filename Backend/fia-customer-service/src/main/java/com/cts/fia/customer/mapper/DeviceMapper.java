package com.cts.fia.customer.mapper;

import com.cts.fia.customer.dto.DeviceDTO;
import com.cts.fia.customer.entity.Device;

public class DeviceMapper {

	// Entity -> DTO
	public static DeviceDTO toDto(Device device) {

		DeviceDTO dto = new DeviceDTO();

		dto.setDeviceId(device.getDeviceId());
		dto.setCustomerId(device.getCustomerId());
		dto.setDeviceName(device.getDeviceName());
		dto.setDeviceType(device.getDeviceType());

		return dto;
	}

	// DTO -> Entity
	public static Device toEntity(DeviceDTO dto) {

		Device device = new Device();

		device.setDeviceId(dto.getDeviceId());
		device.setCustomerId(dto.getCustomerId());
		device.setDeviceName(dto.getDeviceName());
		device.setDeviceType(dto.getDeviceType());

		return device;
	}
}
