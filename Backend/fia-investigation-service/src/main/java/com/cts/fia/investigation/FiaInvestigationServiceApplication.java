package com.cts.fia.investigation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FiaInvestigationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiaInvestigationServiceApplication.class, args);
	}

}
