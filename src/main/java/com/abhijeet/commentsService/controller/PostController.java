package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.models.dto.PostDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostDTO post) throws IOException {
        return new ResponseEntity<Post>(postService.createPost(post), HttpStatus.CREATED);
    }
}
