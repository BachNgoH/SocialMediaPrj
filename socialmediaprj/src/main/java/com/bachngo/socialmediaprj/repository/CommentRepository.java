package com.bachngo.socialmediaprj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bachngo.socialmediaprj.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
