package com.denissinkov.simpleposter.service;

import com.denissinkov.simpleposter.domain.Comment;
import com.denissinkov.simpleposter.repos.CommentRepo;
import com.denissinkov.simpleposter.repos.PostRepo;
import com.denissinkov.simpleposter.repos.UserRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessRedisResponseService {

    private PostRepo postRepo;
    private UserRepo userRepo;
    private CommentRepo commentRepo;

    @Autowired
    public  ProcessRedisResponseService(PostRepo postRepo, UserRepo userRepo, CommentRepo commentRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }

    public void processResponse(String data) {
        JSONObject response = prepareData(data);
        Comment comment = new Comment();
        comment.setPost(postRepo.findById(response.getLong("post")).get());
        comment.setAuthor(userRepo.findByUsername(response.get("author").toString()));
        comment.setText(response.get("text").toString());
        commentRepo.save(comment);

    }

    private JSONObject prepareData(String data) {
        data = data.replace("\\", "");
        return new JSONObject(data.substring(1, data.length()-1));
    }
}
