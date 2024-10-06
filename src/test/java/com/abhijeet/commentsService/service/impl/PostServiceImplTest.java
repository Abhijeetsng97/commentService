package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.models.dto.request.PostRequestDTO;
import com.abhijeet.commentsService.models.entity.Post;
import com.abhijeet.commentsService.repository.PostRepository;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.util.HeaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    @Mock
    private HeaderUtil headerUtil;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost_Success() throws IOException {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setContent("sontent");
        postRequestDTO.setTitle("title");

        when(headerUtil.getUserId()).thenReturn(12345L);
        when(snowflakeIdGeneratorService.getSnowflakeId()).thenReturn(1L);
        when(postRepository.persist(any(Post.class))).thenReturn(null);

        Post createdPost = postServiceImpl.createPost(postRequestDTO);

        assertNotNull(createdPost);
        verify(postRepository, times(1)).persist(any(Post.class));
        verify(headerUtil, times(1)).getUserId();
        verify(snowflakeIdGeneratorService, times(1)).getSnowflakeId();

        assertNotNull(createdPost.getId());
        assertEquals(1L, createdPost.getId());
        assertEquals(12345L, createdPost.getUserId());
        assertEquals("sontent", createdPost.getContent());
        assertEquals("title", createdPost.getTitle());
    }

    @Test
    public void testCreatePost_Exception() throws IOException {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setContent("content");
        postRequestDTO.setTitle("title");

        when(headerUtil.getUserId()).thenReturn(123L);
        when(snowflakeIdGeneratorService.getSnowflakeId()).thenReturn(1L);
        doThrow(new IOException("DB error")).when(postRepository).persist(any(Post.class));

        assertThrows(IOException.class, () -> postServiceImpl.createPost(postRequestDTO));

        verify(postRepository, times(1)).persist(any(Post.class));
    }
}
