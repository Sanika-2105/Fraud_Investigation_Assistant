package com.cts.fia.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be empty")
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	private String password;
	
	@NotBlank(message = "Role cannot be empty")
	private String role;
}
