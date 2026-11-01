package com.cts.fia.auth.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.fia.auth.dto.JwtResponse;
import com.cts.fia.auth.dto.LoginRequest;
import com.cts.fia.auth.dto.RegisterRequestDTO;
import com.cts.fia.auth.dto.RegisterResponseDTO;
import com.cts.fia.auth.entity.User;
import com.cts.fia.auth.entity.repository.UserRepository;
import com.cts.fia.auth.exception.EmailAlreadyExistsException;
import com.cts.fia.auth.exception.InvalidPasswordException;
import com.cts.fia.auth.exception.UserNotFoundException;
import com.cts.fia.auth.security.jwt.JwtUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Register New User
	 */
	public RegisterResponseDTO registerUser(RegisterRequestDTO request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());

		// Encrypt Password
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(request.getRole().toUpperCase());

		User savedUser = userRepository.save(user);

		return new RegisterResponseDTO(savedUser.getUserId(), savedUser.getEmail());
	}

	/**
	 * Required by Spring Security
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities(Collections.emptyList()).build();
	}

	/**
	 * Login User
	 */
	public JwtResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("Invalid Email"));

		boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!passwordMatches) {
			throw new InvalidPasswordException("Invalid Password");
		}

		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

//		return new JwtResponse(token);
		return new JwtResponse(
		        token,
		        user.getRole()
		);
	}

}
