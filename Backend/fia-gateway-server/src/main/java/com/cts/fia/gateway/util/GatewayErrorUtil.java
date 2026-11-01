package com.cts.fia.gateway.util;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GatewayErrorUtil {
	
	 public Mono<Void> unauthorized(
	            ServerWebExchange exchange,
	            String message) {

	        exchange.getResponse()
	                .setStatusCode(HttpStatus.UNAUTHORIZED);

	        exchange.getResponse()
	                .getHeaders()
	                .setContentType(MediaType.APPLICATION_JSON);

	        String body =
	                "{\"status\":401,\"message\":\""
	                        + message + "\"}";

	        DataBuffer buffer =
	                exchange.getResponse()
	                        .bufferFactory()
	                        .wrap(body.getBytes(
	                                StandardCharsets.UTF_8));

	        return exchange.getResponse()
	                .writeWith(Mono.just(buffer));
	    }

}
