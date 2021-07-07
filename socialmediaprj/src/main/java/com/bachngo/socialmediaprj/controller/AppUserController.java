package com.bachngo.socialmediaprj.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachngo.socialmediaprj.dto.AppUserResponse;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.service.AppUserDetailsService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {
	
	private AppUserDetailsService appUserService;
	
	@GetMapping
	public ResponseEntity<List<AppUserResponse>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(appUserService.findAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<AppUserResponse> getUser(@PathVariable Long userId){
		return ResponseEntity.status(HttpStatus.OK).body(appUserService.findUserById(userId));
	}
	
	@GetMapping("/find/{username}")
	public ResponseEntity<List<AppUserResponse>> getUserByUsername(@PathVariable String username){
		return ResponseEntity.status(HttpStatus.OK).body(appUserService.findUserByUsername(username));
	}
}
