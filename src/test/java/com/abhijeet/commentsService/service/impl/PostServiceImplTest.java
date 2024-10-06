package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.mapper.PostMapper;
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
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost_Success() throws IOException {
        PostRequestDTO postRequestDTO = getPostRequestDTO();
        Post post = getPost();

        when(postRepository.persist(any(Post.class))).thenReturn(null);
        when(postMapper.fromDTO(postRequestDTO)).thenReturn(post);
        Post createdPost = postServiceImpl.createPost(postRequestDTO);

        assertNotNull(createdPost);
        verify(postRepository, times(1)).persist(any(Post.class));

        assertNotNull(createdPost.getId());
        assertEquals("content", createdPost.getContent());
        assertEquals("title", createdPost.getTitle());
    }

    @Test
    public void testCreatePost_Exception() throws IOException {
        PostRequestDTO postRequestDTO = getPostRequestDTO();
        Post post = getPost();

        doThrow(new IOException()).when(postRepository).persist(any(Post.class));
        when(postMapper.fromDTO(postRequestDTO)).thenReturn(post);

        assertThrows(IOException.class, () -> postServiceImpl.createPost(postRequestDTO));

        verify(postRepository, times(1)).persist(any(Post.class));
    }

    private PostRequestDTO getPostRequestDTO() {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setContent("content");
        postRequestDTO.setTitle("title");
        return postRequestDTO;
    }

    private Post getPost() {
        Post post = new Post();
        post.setContent("content");
        post.setTitle("title");
        post.setId(12345L);
        return post;
    }
}
