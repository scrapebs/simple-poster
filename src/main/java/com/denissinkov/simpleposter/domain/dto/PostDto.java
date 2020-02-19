package com.denissinkov.simpleposter.domain.dto;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.PostStatus;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.domain.util.PostHelper;

public class PostDto {
    private Long id;
    private String text;
    private User author;
    private String filename;
    private PostStatus status;
    private Long likes;
    private Boolean meLiked;

    public PostDto(Post post, Long likes, Boolean meLiked) {
        this.id = post.getId();
        this.text = post.getText();
        this.author = post.getAuthor();
        this.filename = post.getFilename();
        this.status = post.getStatus();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return PostHelper.getAuthorName(author);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public PostStatus getStatus() {
        return status;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
