package com.cts.fia.auth.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${security.jwt.secret}")
	private String secretKey;

	@Value("${security.jwt.expiry}")
	private long expirationTime;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String generateToken(String email, String role) {

		return Jwts.builder().subject(email).claim("role", role).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(getSigningKey()).compact();
	}

	public Claims validateToken(String token) {

		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

}
