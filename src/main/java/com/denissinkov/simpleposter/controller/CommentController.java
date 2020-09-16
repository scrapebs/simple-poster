package com.denissinkov.simpleposter.controller;

import com.denissinkov.simpleposter.domain.Comment;
import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.domain.dto.AjaxResponse;
import com.denissinkov.simpleposter.domain.dto.CommentDto;
import com.denissinkov.simpleposter.repos.CommentRepo;
import com.denissinkov.simpleposter.repos.PostRepo;
import com.denissinkov.simpleposter.service.AskCommentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import java.util.List;

@RestController
public class CommentController {
    Logger logger = LoggerFactory.getLogger(CommentController.class);

    private CommentRepo commentRepo;
    private PostRepo postRepo;
    private AskCommentService askCommentService;

    @Autowired
    public CommentController(CommentRepo commentRepo, PostRepo postRepo, AskCommentService askCommentService) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.askCommentService = askCommentService;
    }

    @GetMapping(value = "/post/{post}/comments")
    public AjaxResponse getComments(
            @AuthenticationPrincipal User user,
            @PathVariable Post post
    ) {
        List<Comment> comments = commentRepo.findByPost(post);
        if (comments != null && comments.size() !=0)
            return new AjaxResponse("success", comments);
        else
            return new AjaxResponse("not_found", comments);

    }

    @PostMapping(value = "/post/save")
    public AjaxResponse postComment(
            @AuthenticationPrincipal User user,
            @RequestBody CommentDto commentDto
    ) {
        Comment comment = new Comment();
        Post post = postRepo.findById(commentDto.getPostId()).get();
        comment.setAuthor(user);
        comment.setText(commentDto.getText());
        comment.setPost(post);
        commentRepo.save(comment);
        AjaxResponse response = new AjaxResponse("success", null);
        return response;
    }

    @PostMapping("/post/{post}/askComment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity askComment(
        @PathVariable Post post
    ) {
        askCommentService.askComment(post);
        return ResponseEntity.ok("asked");
    }

}
