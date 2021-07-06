package com.bachngo.socialmediaprj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.Post;
import com.bachngo.socialmediaprj.models.React;

public interface ReactRepository extends JpaRepository<React, Long>{

	Optional<React> findByPostAndUser(Post post, AppUser appUser);

}
