package com.bachngo.socialmediaprj.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ConfirmationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String token;
	
	@Column(nullable = false)
	private Instant createdAt;
	
	@Column(nullable = false)
	private Instant expiresAt;
	private Instant confirmedAt;
	
	@ManyToOne
	@JoinColumn(nullable = false, name="app_user_id")
	private AppUser appUser;

	
}
