package com.cts.fia.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cts.fia.gateway.security.jwt.JwtUtil;
import com.cts.fia.gateway.util.GatewayErrorUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter {
	private final JwtUtil jwtUtil;
	private final GatewayErrorUtil gatewayErrorUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, GatewayErrorUtil gatewayErrorUtil) {

		this.jwtUtil = jwtUtil;
		this.gatewayErrorUtil = gatewayErrorUtil;
	}

	public Mono<Void> filter(ServerWebExchange exchange) {

		String path = exchange.getRequest().getURI().getPath();

		// Public APIs
		if (path.startsWith("/auth")) {
			return Mono.empty();
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			return gatewayErrorUtil.unauthorized(exchange, "Missing JWT Token");
		}

		String token = authHeader.substring(7);

		try {

			Claims claims = jwtUtil.validateToken(token);

			String role = claims.get("role", String.class);

			String requestPath = exchange.getRequest().getURI().getPath();

			/*
			 * ADMIN only APIs
			 */
			if (requestPath.startsWith("/customers") || requestPath.startsWith("/devices")) {

				if (!"ADMIN".equalsIgnoreCase(role)) {

					return gatewayErrorUtil.unauthorized(exchange, "Access Denied. ADMIN role required.");
				}
			}

			/*
			 * ADMIN and ANALYST APIs
			 */
			if (requestPath.startsWith("/transactions") || requestPath.startsWith("/investigations")) {

				if (!("ADMIN".equalsIgnoreCase(role) || "ANALYST".equalsIgnoreCase(role))) {

					return gatewayErrorUtil.unauthorized(exchange, "Access Denied.");
				}
			}

			exchange.getAttributes().put("email", claims.getSubject());

			exchange.getAttributes().put("role", role);

			return Mono.empty();

		} catch (Exception ex) {

			return gatewayErrorUtil.unauthorized(exchange, "Invalid JWT Token");
		}
	}

}
