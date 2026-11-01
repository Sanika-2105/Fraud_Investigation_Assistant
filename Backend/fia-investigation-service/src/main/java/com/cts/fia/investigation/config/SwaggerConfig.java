package com.cts.fia.investigation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
    public OpenAPI agencyOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Investigation Service API")
                .description("APIs for Transaction Analysis, Risk Assessment and Investigation Management")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Sanika Joshi")
                    .email("SanikaVijay.Joshi@cognizant.com")));
    }
}
