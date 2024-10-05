package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.PostDTO;
import com.abhijeet.commentsService.models.entity.Post;

import java.io.IOException;

public interface PostService {
    Post createPost(PostDTO post) throws IOException;
}
