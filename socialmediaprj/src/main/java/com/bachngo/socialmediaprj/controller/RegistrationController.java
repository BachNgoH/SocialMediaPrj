package com.bachngo.socialmediaprj.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bachngo.socialmediaprj.dto.RegistrationRequest;
import com.bachngo.socialmediaprj.service.RegistrationService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	
	@PostMapping
	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}
	
	@GetMapping(path="confirm")
	public String confirmUser(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
	
}
