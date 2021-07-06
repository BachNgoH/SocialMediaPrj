package com.bachngo.socialmediaprj.controller;

import java.util.List;

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

import com.bachngo.socialmediaprj.dto.CommentRequest;
import com.bachngo.socialmediaprj.dto.CommentResponse;
import com.bachngo.socialmediaprj.models.Comment;
import com.bachngo.socialmediaprj.service.CommentService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<String> postComment(@RequestBody CommentRequest commentRequest) {
		commentService.saveComment(commentRequest);
		return ResponseEntity.status(HttpStatus.OK).body("success!!");
	};
	
	@GetMapping("/{postId}")
	public ResponseEntity<List<CommentResponse>> findAllCommentsOfPost(@PathVariable Long postId){
		List<CommentResponse> comments = commentService.getAllCommentOfAPost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}
	
}
