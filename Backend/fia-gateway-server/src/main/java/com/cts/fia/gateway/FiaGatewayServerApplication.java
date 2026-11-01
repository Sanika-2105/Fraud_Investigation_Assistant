package com.cts.fia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import com.cts.fia.gateway.filter.JwtAuthenticationFilter;

@SpringBootApplication
public class FiaGatewayServerApplication {

	private final JwtAuthenticationFilter jwtFilter;

	public FiaGatewayServerApplication(JwtAuthenticationFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(FiaGatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator customRoutings(RouteLocatorBuilder builder) {

		return builder.routes()

				.route("customer-route", r -> r.path("/customers/**", "/devices/**").filters(
						f -> f.filter((exchange, chain) -> jwtFilter.filter(exchange).then(chain.filter(exchange))))
						.uri("lb://fia-customer-service"))

				.route("investigation-route", r -> r.path("/transactions/**", "/investigations/**").filters(
						f -> f.filter((exchange, chain) -> jwtFilter.filter(exchange).then(chain.filter(exchange))))
						.uri("lb://fia-investigation-service"))

				.build();
	}

}
