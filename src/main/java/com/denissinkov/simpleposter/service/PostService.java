package com.denissinkov.simpleposter.service;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.domain.dto.PostDto;
import com.denissinkov.simpleposter.repos.PostRepo;
import com.denissinkov.simpleposter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PostService {
    private PostRepo postRepo;
    private UserRepo userRepo;

    @Autowired
    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public Page<PostDto> postList(Pageable pageable, String filterUserName, User user) {
        if(filterUserName != null && !filterUserName.isEmpty() ) {
            User author = userRepo.findByUsername(filterUserName);
            return postRepo.findByAuthor(pageable, author, user);
        }
        else {
            return postRepo.findAll(pageable, user);
        }
    }

    public Page<PostDto> PostListForUser(Pageable pageable, User author, User currentUser) {
        return postRepo.findByAuthor(pageable, author, currentUser);
    }
}
