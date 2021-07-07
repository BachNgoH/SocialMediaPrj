package com.bachngo.socialmediaprj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.controller.FriendConnectionController;
import com.bachngo.socialmediaprj.dto.AppUserResponse;
import com.bachngo.socialmediaprj.dto.ConnectionResponse;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.FriendConnection;
import com.bachngo.socialmediaprj.repository.AppUserRepository;
import com.bachngo.socialmediaprj.repository.FriendConnectionRepository;
import com.sun.jdi.connect.spi.Connection;

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

	public ConnectionResponse findConnection(Long userId) {
		AppUser currentUser = appUserDetailsService.getCurrentUser();
		AppUser user = appUserRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("user not found"));
		Optional<FriendConnection> connectionOptional = 
				friendConnectionRepository.findByRequestderAndRequestdee(currentUser, user);
		Optional<FriendConnection> connectionOptional2 = 
				friendConnectionRepository.findByRequestderAndRequestdee(user, currentUser);
		if(currentUser.getId() == user.getId()) {
			return new ConnectionResponse("SELF");
		}
		if(connectionOptional.isPresent()) {
			FriendConnection connection = connectionOptional.get();
			if(connection.isAccepted()) {
				return new ConnectionResponse("FRIEND");
			}else {
				return new ConnectionResponse("REQUESTED");
			}
		}
		if(connectionOptional2.isPresent()) {
			FriendConnection connection = connectionOptional2.get();
			if(connection.isAccepted()) {
				return new ConnectionResponse("FRIEND");
			}else {
				return new ConnectionResponse("REQUESTED");
			}
		}

		return new ConnectionResponse("NO CONNECTION");
	}

	public List<AppUserResponse> findAllFriendsOfUser() {
		AppUser currentUser = appUserDetailsService.getCurrentUser();
		List<FriendConnection> connections = friendConnectionRepository
				.findAllByRequestderAndAccepted(currentUser, true);
		List<FriendConnection> connectionsReversed = friendConnectionRepository
				.findAllByRequestdeeAndAccepted(currentUser, true);
		List<AppUser> friends = new ArrayList<AppUser>();
		for(FriendConnection connection: connections) {
			friends.add(connection.getRequestdee());
		}
		for(FriendConnection connection: connectionsReversed) {
			friends.add(connection.getRequestder());
		}

		List<AppUserResponse> responses = new ArrayList<AppUserResponse>();
		for(AppUser friend: friends) {
			responses.add(AppUserResponse.builder()
					.lastName(friend.getLastName()).firstName(friend.getFirstName())
					.userId(friend.getId()).build());
		}
		return responses;
	}

	public List<AppUserResponse> findAllRequestOfUser() {
		AppUser currentUser = appUserDetailsService.getCurrentUser();
		List<FriendConnection> connectionsReversed = friendConnectionRepository
				.findAllByRequestdeeAndAccepted(currentUser, false);
		List<AppUserResponse> responses = new ArrayList<AppUserResponse>();
		for(FriendConnection connection: connectionsReversed) {
			AppUser friend = connection.getRequestder();
			responses.add(AppUserResponse.builder().firstName(friend.getFirstName())
					.lastName(friend.getLastName()).userId(friend.getId()).build());
		}

		return responses;
	}


}
