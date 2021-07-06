package com.bachngo.socialmediaprj.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	
	private Instant createdAt;
	
	@JsonIgnore
	@OneToMany( mappedBy = "post")
	private List<React> reacts;
	
	@Lob
	private String content;
	
	
	
}
