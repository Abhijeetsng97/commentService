package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.PostRequestDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final HeaderUtil headerUtil;
    private final SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    public Post fromDTO(PostRequestDTO postRequestDTO) {
        Post post = new Post();
        post.setContent(postRequestDTO.getContent());
        post.setTitle(postRequestDTO.getTitle());
        post.setUserId(headerUtil.getUserId());
        post.setId(snowflakeIdGeneratorService.getSnowflakeId());
        post.setCreatedAt(System.currentTimeMillis());
        post.setUpdatedAt(System.currentTimeMillis());
        post.setLikesCount(0L);
        post.setDislikesCount(0L);
        return post;
    }
}
