package com.cts.fia.auth.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	
	@NotBlank(message = "Name cannot be empty")
	@Column(nullable = false)
	private String name;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be empty")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "Role cannot be empty")
	@Column(nullable = false)
	private String role;

}
