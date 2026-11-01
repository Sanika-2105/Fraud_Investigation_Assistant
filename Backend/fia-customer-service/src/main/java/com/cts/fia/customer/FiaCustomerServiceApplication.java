package com.cts.fia.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FiaCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiaCustomerServiceApplication.class, args);
	}

}
