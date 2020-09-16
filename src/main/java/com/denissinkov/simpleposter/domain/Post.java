package com.denissinkov.simpleposter.domain;

import com.denissinkov.simpleposter.domain.util.PostHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill in the field")
    @Length(max = 2048, message = "Text is too long")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    private String filename;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();


    public Post() {
    }

    public Post(String text, User user) {
        this.author = user;
        this.text = text;
        this.status = PostStatus.NEW;
    }


    public String getAuthorName() {
        return PostHelper.getAuthorName(author);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String postText) {
        this.text = postText;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public PostStatus getStatus() { return status; }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getFilename() { return filename;  }

    public void setFilename(String filename) { this.filename = filename; }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }
}