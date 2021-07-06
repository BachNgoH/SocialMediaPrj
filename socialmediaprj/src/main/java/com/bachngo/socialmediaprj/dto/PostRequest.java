package com.bachngo.socialmediaprj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
	private Long userId;
	private String content;
}
