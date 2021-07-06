package com.bachngo.socialmediaprj.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.FriendConnection;
import com.bachngo.socialmediaprj.repository.AppUserRepository;
import com.bachngo.socialmediaprj.repository.FriendConnectionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FriendConnectionService {
	
	private final FriendConnectionRepository friendConnectionRepository;
	private final AppUserDetailsService appUserDetailsService;
	private final AppUserRepository appUserRepository;
	
	public void sendFriendRequest(Long requestdeeId) {
		AppUser requestdee = appUserRepository.findById(requestdeeId)
				.orElseThrow(() -> new IllegalStateException("User not Found"));
		AppUser requestder = appUserDetailsService.getCurrentUser();
		if(requestder.getId().equals(requestdee.getId())) {
			throw new IllegalStateException("You can't send request to yourself");
		}
		Optional<FriendConnection> connection =
				friendConnectionRepository.findByRequestderAndRequestdee(requestder, requestdee);
		Optional<FriendConnection> connectionReverse =
				friendConnectionRepository.findByRequestderAndRequestdee(requestdee, requestder);
		if(connection.isPresent() || connectionReverse.isPresent()) {
			throw new IllegalStateException("Connection already formed");
		}
		FriendConnection newConnection = FriendConnection.builder().requestdee(requestdee)
				.requestder(requestder).build();
		friendConnectionRepository.save(newConnection);
	}

	public void sendAcceptRequest(Long requestderId) {
		AppUser requestder = appUserRepository.findById(requestderId)
				.orElseThrow(() -> new IllegalStateException("User not Found"));
		AppUser requestdee = appUserDetailsService.getCurrentUser();
		FriendConnection connection =
				friendConnectionRepository.findByRequestderAndRequestdee(requestder, requestdee)
				.orElseThrow(() -> new IllegalStateException("Connection Not Found!"));
		connection.setAccepted(true);
		friendConnectionRepository.save(connection);
	}

	public void sendUnfriendRequest(Long requestdeeId) {
		AppUser requestder = appUserRepository.findById(requestdeeId)
				.orElseThrow(() -> new IllegalStateException("User not Found"));
		AppUser requestdee = appUserDetailsService.getCurrentUser();
		Optional<FriendConnection> connection =
				friendConnectionRepository.findByRequestderAndRequestdee(requestder, requestdee);

		Optional<FriendConnection> connectionReverse =
				friendConnectionRepository.findByRequestderAndRequestdee(requestdee, requestder);
		if(connection.isPresent()) {
			friendConnectionRepository.delete(connection.get());
		}
		if(connectionReverse.isPresent()) {
			friendConnectionRepository.delete(connectionReverse.get());
		}

	}
	
	
}
