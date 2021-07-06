package com.bachngo.socialmediaprj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
	
	public Long postId;
	public Long userId;
	public String username;
	public String text;
}
