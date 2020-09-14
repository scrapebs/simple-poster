package com.denissinkov.simpleposter.service;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.queue.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskCommentService {

    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    public void askComment(Post post) {
        redisMessagePublisher.publish(post);
    }
}
