package com.denissinkov.simpleposter.repos;

import com.denissinkov.simpleposter.domain.Comment;
import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
     List<Comment> findByPost(Post post);
     List<Comment> findByAuthor(User user);
}
