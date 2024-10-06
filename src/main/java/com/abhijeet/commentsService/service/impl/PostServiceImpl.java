package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.models.dto.PostDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.repository.PostRepository;
import com.abhijeet.commentsService.service.PostService;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    @Autowired
    private HeaderUtil headerUtil;

    @Override
    public Post createPost(PostDTO postDTO) throws IOException {
        Post post = initPostCreation(postDTO);
        postRepository.persist(post);
        return post;
    }

    private Post initPostCreation(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setUserId(headerUtil.getUserId());
        post.setId(snowflakeIdGeneratorService.getSnowflakeId());
        post.setCreatedAt(System.currentTimeMillis());
        post.setUpdatedAt(System.currentTimeMillis());
        post.setLikesCount(0L);
        post.setDislikesCount(0L);
        return post;
    }
}
