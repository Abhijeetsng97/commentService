package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.mapper.PostMapper;
import com.abhijeet.commentsService.models.dto.request.PostRequestDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.repository.PostRepository;
import com.abhijeet.commentsService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post createPost(PostRequestDTO postRequestDTO) throws IOException {
        Post post = postMapper.fromDTO(postRequestDTO);
        postRepository.persist(post);
        return post;
    }
}
