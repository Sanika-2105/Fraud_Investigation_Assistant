package com.cts.fia.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.fia.customer.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
	
	List<Device> findByCustomerId(String customerId);

}
