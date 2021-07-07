package com.bachngo.socialmediaprj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bachngo.socialmediaprj.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByEmail(String email);

	List<AppUser> findByFirstNameOrLastNameContainingIgnoreCase(String username, String username2);
}
