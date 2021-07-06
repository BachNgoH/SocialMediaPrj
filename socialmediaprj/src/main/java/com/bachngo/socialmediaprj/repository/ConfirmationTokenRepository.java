package com.bachngo.socialmediaprj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bachngo.socialmediaprj.models.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{

	Optional<ConfirmationToken> findByToken(String token);

}
