package com.bachngo.socialmediaprj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {
	
	@Bean
	BCryptPasswordEncoder passwordEconder() {
		return new BCryptPasswordEncoder();
	}
}
