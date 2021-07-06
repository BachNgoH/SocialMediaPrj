package com.bachngo.socialmediaprj.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.dto.AppUserResponse;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.ConfirmationToken;
import com.bachngo.socialmediaprj.repository.AppUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	@Override
	public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
	}
	
	public AppUser getCurrentUser() {
		AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}
	
	public String signUpUser(AppUser user) {
		boolean userExist = appUserRepository.findByEmail(user.getEmail()).isPresent();
		if(userExist) {
			throw new IllegalStateException("Email already taken");
		}
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		
		appUserRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationToken = ConfirmationToken.builder().appUser(user)
				.createdAt(Instant.now()).expiresAt(Instant.now().plusSeconds(60*60)).token(token)
				.build();
		
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		return token;
		
	}

	public boolean enableAppUser(String email) {
		AppUser appUser = appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException( "User at Email: "+ email +" Not Found"));
		appUser.setEnabled(true);
		appUserRepository.save(appUser);
		return true;
	}
	
	public AppUserResponse findUserById(Long userId) {
		
		AppUser appUser = getCurrentUser();
		
		AppUserResponse userResponse = AppUserResponse.builder()
				.email(appUser.getEmail()).firstName(appUser.getFirstName())
				.lastName(appUser.getLastName()).userId(userId).build();
		return userResponse;
	}
}
