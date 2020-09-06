package com.denissinkov.simpleposter.controller;

import com.denissinkov.simpleposter.domain.Post;
import com.denissinkov.simpleposter.domain.PostStatus;
import com.denissinkov.simpleposter.domain.User;
import com.denissinkov.simpleposter.domain.dto.PostDto;
import com.denissinkov.simpleposter.repos.PostRepo;
import com.denissinkov.simpleposter.repos.UserRepo;
import com.denissinkov.simpleposter.service.PostService;
import com.denissinkov.simpleposter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class PosterController {

    private PostRepo postRepo;
    private UserRepo userRepo;
    private PostService postService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PosterController(PostRepo postRepo, UserRepo userRepo, PostService postService) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.postService = postService;
    }

    @GetMapping(path = "/post")
    public String posts(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        Page<PostDto> page = postService.postList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/post");
        model.addAttribute("filter", filter);
        return "posts";
    }

    @PostMapping(path="/post")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
            ) throws IOException {
        post.setAuthor(user);
        post.setStatus(PostStatus.NEW);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {
            saveFile(post, file);
            model.addAttribute("post", null);
            postRepo.save(post);
        }

        Iterable<Post> posts = postRepo.findAll();

        model.addAttribute("posts", posts);

        if (bindingResult.hasErrors()) {
            return "posts";
        } else {
            return "redirect:/post";
        }
    }

    private void saveFile(@Valid Post post, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            post.setFilename(resultFileName);
        }
    }

    @GetMapping("/user-posts/{author}")
    public String userPosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Post post,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PostDto> page = postService.postListForUser(pageable, author, currentUser);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("post", post);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-posts/" + author.getId());

        return "userPosts";
    }

    @PostMapping("/user-posts/{user}")
    public String updatePost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user")  User viewedUser,
            @Valid Post post,
            @RequestParam("file") MultipartFile file,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (viewedUser.equals(currentUser)) {
            post.setAuthor(viewedUser);
            post.setStatus(PostStatus.NEW);

            if (bindingResult.hasErrors()) {
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
                model.mergeAttributes(errorsMap);
                model.addAttribute("post", post);
            } else {
                saveFile(post, file);
                model.addAttribute("post", null);
                postRepo.save(post);
            }
        }
        return "redirect:/user-posts/" + viewedUser.getId();
    }

    @PostMapping("/post/{post}/delete")
    public String deletePost(
            @AuthenticationPrincipal User user,
            @PathVariable Post post

    ) {
        postService.deletePost(user, post);
        return "redirect:/user-posts/" + user.getId();
    }

    @GetMapping("/posts/{post}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = post.getLikes();

        if(likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else  {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));


        return "redirect:" + components.getPath();
    }
}