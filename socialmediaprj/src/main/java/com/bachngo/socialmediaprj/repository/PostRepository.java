package com.bachngo.socialmediaprj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bachngo.socialmediaprj.models.AppUser;
import com.bachngo.socialmediaprj.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findAllByUserOrderByIdDesc(Long userId);

	List<Post> findAllByUserOrderByIdDesc(AppUser user);

	//List<Post> findAllOrderByIdDesc();

}
