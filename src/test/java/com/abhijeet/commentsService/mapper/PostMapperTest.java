package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.PostRequestDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.util.HeaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostMapperTest {
    @Mock
    private HeaderUtil headerUtil;

    @Mock
    private SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    @InjectMocks
    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFromDTO() {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setContent("test post.");
        postRequestDTO.setTitle("Title");

        when(headerUtil.getUserId()).thenReturn(42L);
        when(snowflakeIdGeneratorService.getSnowflakeId()).thenReturn(1231L);

        Post post = postMapper.fromDTO(postRequestDTO);

        assertEquals("test post.", post.getContent());
        assertEquals("Title", post.getTitle());
        assertEquals(42L, post.getUserId());
        assertEquals(1231L, post.getId());
        assertEquals(0L, post.getLikesCount());
        assertEquals(0L, post.getDislikesCount());
    }
}