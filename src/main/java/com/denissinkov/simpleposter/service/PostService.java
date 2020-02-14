package com.denissinkov.simpleposter.service;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.repos.PostRepo;
import com.denissinkov.simpleposter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostRepo postRepo;
    private UserRepo userRepo;

    @Autowired
    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public Page<Post> postList(Pageable pageable, String filter) {
        if(filter != null && !filter.isEmpty() ) {
            User author = userRepo.findByUsername(filter);
            return postRepo.findByAuthor(author, pageable);
        }
        else {
            return postRepo.findAll(pageable);
        }
    }

    public Page<Post> PostListForUser(Pageable pageable, User currentUser, User author) {
        return postRepo.findByAuthor(author, pageable);
    }
}
