package com.bachngo.socialmediaprj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachngo.socialmediaprj.dto.AuthenticationRequest;
import com.bachngo.socialmediaprj.dto.AuthenticationResponse;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.security.JwtUtil;
import com.bachngo.socialmediaprj.service.AppUserDetailsService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	private AppUserDetailsService userDetailsService;
	private JwtUtil jwtUtil;

	@PostMapping
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password ", e);
		}
		
		final AppUser userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getId()));
	}
	
}
