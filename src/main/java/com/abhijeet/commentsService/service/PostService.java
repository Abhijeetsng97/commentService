package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.request.PostRequestDTO;
import com.abhijeet.commentsService.models.entity.Post;

import java.io.IOException;

public interface PostService {
    Post createPost(PostRequestDTO post) throws IOException;
}
