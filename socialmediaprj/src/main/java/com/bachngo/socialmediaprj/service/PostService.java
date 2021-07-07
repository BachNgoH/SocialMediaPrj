package com.bachngo.socialmediaprj.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.dto.PostRequest;
import com.bachngo.socialmediaprj.dto.PostResponse;
import com.bachngo.socialmediaprj.dto.ReactCountResponse;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.Post;
import com.bachngo.socialmediaprj.models.React;
import com.bachngo.socialmediaprj.models.ReactStatus;
import com.bachngo.socialmediaprj.repository.AppUserRepository;
import com.bachngo.socialmediaprj.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final AppUserRepository appUserRepository;
	private final AppUserDetailsService appUserDetailsService;
	private final ReactService reactService;

	public List<PostResponse> getAllPostsOfAUser(Long userId) {

		AppUser currentUser = appUserDetailsService.getCurrentUser();
		AppUser user = appUserRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("user at this id not found!"));
		List<Post> posts = postRepository.findAllByUserOrderByIdDesc(user);

		List<PostResponse> allPostResponses = new ArrayList<>();
		for(Post post: posts) {
			Optional<React> reactOpt = reactService.findByPostAndUser(post, currentUser);

			String userReact = "NULL";
			if(reactOpt.isPresent()) {
				React tempReact = reactOpt.get();
				if(tempReact.getType().equals(ReactStatus.LIKE)){
					userReact = "LIKE";
				}else if(tempReact.getType().equals(ReactStatus.DISLIKE)) {
					userReact = "DISLIKE";
				}
			}

			AppUser postUser = post.getUser();
			ReactCountResponse reactCount = reactService.countReacts(post.getId());
			allPostResponses.add(PostResponse.builder()
					.createdAt(post.getCreatedAt()).postId(post.getId()).dislikeCount(reactCount.disLikes)
					.likeCount(reactCount.likes).content(post.getContent()).userFirstName(postUser.getFirstName())
					.userLastName(postUser.getLastName()).commentCount(post.getComments().size())
					.userReact(userReact)
					.build());
		}
		return allPostResponses;

	}

	public List<PostResponse> getAllPosts(){
		List<Post> allPosts = postRepository.findAll();
		List<PostResponse> allPostResponses = new ArrayList<>();
		AppUser currentUser = appUserDetailsService.getCurrentUser();

		for(Post post: allPosts) {
			Optional<React> reactOpt = reactService.findByPostAndUser(post, currentUser);

			String userReact = "NULL";
			if(reactOpt.isPresent()) {
				React tempReact = reactOpt.get();
				if(tempReact.getType().equals(ReactStatus.LIKE)){
					userReact = "LIKE";
				}else if(tempReact.getType().equals(ReactStatus.DISLIKE)) {
					userReact = "DISLIKE";
				}
			}

			AppUser postUser = post.getUser();
			ReactCountResponse reactCount = reactService.countReacts(post.getId());
			allPostResponses.add(PostResponse.builder()
					.createdAt(post.getCreatedAt()).postId(post.getId()).dislikeCount(reactCount.disLikes)
					.likeCount(reactCount.likes).content(post.getContent()).userFirstName(postUser.getFirstName())
					.userLastName(postUser.getLastName()).commentCount(post.getComments().size())
					.userReact(userReact)
					.build());
		}
		return allPostResponses;
	}

	public void save(PostRequest postRequest) {
		AppUser user = appUserDetailsService.getCurrentUser();
		Post newPost = Post.builder().createdAt(Instant.now()).user(user)
				.content(postRequest.getContent()).build();
		postRepository.save(newPost);
	}




}
