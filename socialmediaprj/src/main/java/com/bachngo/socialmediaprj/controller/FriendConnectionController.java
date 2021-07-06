package com.bachngo.socialmediaprj.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachngo.socialmediaprj.service.FriendConnectionService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/friends")
@AllArgsConstructor
public class FriendConnectionController {
	
	private final FriendConnectionService friendConnectionService;
	
	@PostMapping("/sendFriendRequest/{requestdeeId}")
	public ResponseEntity<String> sendFriendRequest(@PathVariable Long requestdeeId){
		friendConnectionService.sendFriendRequest(requestdeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
	
	@PostMapping("/sendAcceptRequest/{requestderId}")
	public ResponseEntity<String> acceptRequest(@PathVariable Long requestderId){
		friendConnectionService.sendAcceptRequest(requestderId);
		return ResponseEntity.status(HttpStatus.OK).body("Accepted!!");
	}
	
	@PostMapping("/sendUnfriendRequest/{requestdeeId}")
	public ResponseEntity<String> sendUnfriendRequest(@PathVariable Long requestdeeId){
		friendConnectionService.sendUnfriendRequest(requestdeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Unfriended");
	}

}
