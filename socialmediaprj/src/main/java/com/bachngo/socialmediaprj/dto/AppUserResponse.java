package com.bachngo.socialmediaprj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AppUserResponse {
	
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	
}
