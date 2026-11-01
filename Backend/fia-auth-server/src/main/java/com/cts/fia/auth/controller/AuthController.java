package com.cts.fia.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fia.auth.dto.JwtResponse;
import com.cts.fia.auth.dto.LoginRequest;
import com.cts.fia.auth.dto.RegisterRequestDTO;
import com.cts.fia.auth.dto.RegisterResponseDTO;
import com.cts.fia.auth.service.CustomUserDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(
	    name = "Authentication API",
	    description = "Operations related to user registration, authentication, and JWT token generation"
	)
public class AuthController {

	private final CustomUserDetailsService userDetailsService;

	public AuthController(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Operation(
		    summary = "Register a new user",
		    description = "Create a new user account with the specified role and return registration details"
		)
	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {

		RegisterResponseDTO response = userDetailsService.registerUser(request);

		return ResponseEntity.ok(response);
	}
	@Operation(
		    summary = "Authenticate user",
		    description = "Validate user credentials and generate a JWT token for accessing secured APIs"
		)

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {

		JwtResponse response = userDetailsService.login(request);

		return ResponseEntity.ok(response);
	}

}
