package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.mapper.CommentMapper;
import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static com.abhijeet.commentsService.constant.AppConstants.DELETED_DATA;
import static com.abhijeet.commentsService.constant.AppConstants.TOP_LEVEL_COMMENT_PARENT_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCommentToPost() throws IOException {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setContent("comment");
        long postId = 12345L;

        Comment comment = new Comment();
        when(commentMapper.fromDTO(commentRequestDTO, postId, TOP_LEVEL_COMMENT_PARENT_ID)).thenReturn(comment);

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        when(commentMapper.toDTO(comment)).thenReturn(responseDTO);

        CommentResponseDTO result = commentService.addCommentToPost(commentRequestDTO, postId);
        assertNotNull(result);
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).toDTO(comment);
    }

    @Test
    public void testDeleteComment_true() throws IOException {
        long commentId = 123L;
        String rowKey = "1:1:1";
        when(commentRepository.fetchRowKeyForId(commentId)).thenReturn(rowKey);

        Comment comment = new Comment();
        comment.setContent("comment");
        comment.setId(1L);
        when(commentRepository.get(rowKey)).thenReturn(comment);

        Boolean result = commentService.deleteComment(commentId);

        assertTrue(result);
        verify(commentRepository, times(1)).persist(comment);
        assertEquals(DELETED_DATA, comment.getContent());
    }

    @Test
    public void testDeleteComment_false() throws IOException {
        long commentId = 123L;
        String rowKey = "1:1:1";
        when(commentRepository.fetchRowKeyForId(commentId)).thenReturn(rowKey);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent(DELETED_DATA);
        when(commentRepository.get(rowKey)).thenReturn(comment);

        Boolean result = commentService.deleteComment(commentId);

        assertFalse(result);
    }
}