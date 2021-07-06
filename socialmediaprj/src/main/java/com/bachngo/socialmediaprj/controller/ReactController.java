package com.bachngo.socialmediaprj.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bachngo.socialmediaprj.dto.ReactCountResponse;
import com.bachngo.socialmediaprj.dto.ReactRequest;
import com.bachngo.socialmediaprj.service.ReactService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/react")
@AllArgsConstructor
public class ReactController {
	
	private final ReactService reactService;
	
	@PostMapping
	public ResponseEntity<String> reactAPost(@RequestBody ReactRequest request){
		reactService.saveReact(request);
		return ResponseEntity.status(HttpStatus.OK).body("React Successful!!");
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<ReactCountResponse> getReactCountOfAPost(@PathVariable Long postId){
		ReactCountResponse counter = reactService.countReacts(postId);
		return ResponseEntity.status(HttpStatus.OK).body(counter);
	}
	
}
