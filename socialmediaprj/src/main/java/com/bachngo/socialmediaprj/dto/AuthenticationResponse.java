package com.bachngo.socialmediaprj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

	private final String jwt;
	private final Long userId;
}
