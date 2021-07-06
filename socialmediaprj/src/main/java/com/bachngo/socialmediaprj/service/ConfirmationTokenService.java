package com.bachngo.socialmediaprj.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.models.ConfirmationToken;
import com.bachngo.socialmediaprj.repository.ConfirmationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
	
	private final ConfirmationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	
	public Optional<ConfirmationToken> getToken(String token){
		return confirmationTokenRepository.findByToken(token);
	}
	
	public boolean setConfirmedAt(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalStateException("Token not found"));
		confirmationToken.setConfirmedAt(Instant.now());
		confirmationTokenRepository.save(confirmationToken);
		return true;
		
	}
	
}
