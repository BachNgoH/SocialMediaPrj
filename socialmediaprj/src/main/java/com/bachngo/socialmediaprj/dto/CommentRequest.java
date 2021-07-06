package com.bachngo.socialmediaprj.dto;

import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
	private Long userId;
	private Long postId;
	private String text;
	
}
