package com.bachngo.socialmediaprj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bachngo.socialmediaprj.dto.ReactCountResponse;
import com.bachngo.socialmediaprj.dto.ReactRequest;
import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.Post;
import com.bachngo.socialmediaprj.models.React;
import com.bachngo.socialmediaprj.models.ReactStatus;
import com.bachngo.socialmediaprj.repository.AppUserRepository;
import com.bachngo.socialmediaprj.repository.PostRepository;
import com.bachngo.socialmediaprj.repository.ReactRepository;
import com.sun.mail.handlers.text_html;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReactService {

	private ReactRepository reactRepository;
	private PostRepository postRepository;
	private AppUserDetailsService appUserDetailsService;

	public void saveReact(ReactRequest reactRequest) {
		Post post = postRepository.findById(reactRequest.postId)
				.orElseThrow(() -> new IllegalStateException("Post not found"));
		AppUser user = appUserDetailsService.getCurrentUser();
		Optional<React> testOp = reactRepository.findByPostAndUser(post, user);
		if(testOp.isPresent()) {
			React test = testOp.get();
			if(test.getType() == reactRequest.reactStatus) {
				reactRepository.delete(test);
			}
			test.setType(reactRequest.getReactStatus());
			reactRepository.save(test);
		}else {
			React react = React.builder()
					.user(user).post(post).type(reactRequest.reactStatus).build();
			reactRepository.save(react);
		}
	}

	public Optional<React> findByPostAndUser( Post post, AppUser user){
		return reactRepository.findByPostAndUser(post, user);
	}

	public ReactCountResponse countReacts(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalStateException("Post not found"));
		List<React> reactsList = post.getReacts();
		Long likesCount = 0L;
		Long disLikesCount = 0L;
		for(React react: reactsList) {
			if(react.getType() == ReactStatus.LIKE) likesCount++;
			if(react.getType() == ReactStatus.DISLIKE) disLikesCount++;
		}
		return new ReactCountResponse(likesCount, disLikesCount);
	}
}
