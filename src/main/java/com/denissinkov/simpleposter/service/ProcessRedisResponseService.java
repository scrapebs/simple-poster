package com.denissinkov.simpleposter.service;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessRedisResponseService {

    private PostRepo postRepo;

    @Autowired
    public  ProcessRedisResponseService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public void processResponse(String data) {

    }
}
