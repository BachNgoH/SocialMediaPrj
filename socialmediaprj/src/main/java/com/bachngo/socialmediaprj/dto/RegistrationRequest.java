package com.bachngo.socialmediaprj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RegistrationRequest {

	private final String firstName;
	private final String lastName;
	private final String password;
	private final String email;
}
