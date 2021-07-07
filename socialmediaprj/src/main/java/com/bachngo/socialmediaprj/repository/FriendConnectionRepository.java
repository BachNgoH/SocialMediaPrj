package com.bachngo.socialmediaprj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bachngo.socialmediaprj.controller.FriendConnectionController;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.FriendConnection;

@Repository
public interface FriendConnectionRepository extends JpaRepository<FriendConnection, Long>{

	Optional<FriendConnection> findByRequestderAndRequestdee(AppUser requestder, AppUser requestdee);
	List<FriendConnection> findAllByRequestderAndAccepted(AppUser currentUser, boolean isAccepted);
	List<FriendConnection> findAllByRequestdeeAndAccepted(AppUser currentUser, boolean isAccepted);

}
