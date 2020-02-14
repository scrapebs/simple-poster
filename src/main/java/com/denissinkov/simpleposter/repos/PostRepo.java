package com.denissinkov.simpleposter.repos;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthor(User author, Pageable pageable);
}