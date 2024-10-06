package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.service.UserService;
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
public class CommentMapperTest {

    @Mock
    private HeaderUtil headerUtil;

    @Mock
    private SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFromDTO() {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setContent("comment.");
        long postId = 1L;
        long parentCommentId = 0L;
        when(headerUtil.getUserId()).thenReturn(42L);
        when(snowflakeIdGeneratorService.getSnowflakeId()).thenReturn(12L);

        Comment comment = commentMapper.fromDTO(commentRequestDTO, postId, parentCommentId);

        assertEquals("comment.", comment.getContent());
        assertEquals(12L, comment.getId());
        assertEquals(42L, comment.getUserId());
        assertEquals(postId, comment.getPostId());
        assertEquals(parentCommentId, comment.getParentCommentId());
        assertEquals(false, comment.getHasChildComments());
        assertEquals(0L, comment.getLikesCount());
        assertEquals(0L, comment.getDislikesCount());
    }

    @Test
    public void testToDTO() {
        Comment comment = new Comment();
        comment.setId(12L);
        comment.setUserId(42L);
        comment.setPostId(1L);
        comment.setContent("comment.");
        comment.setParentCommentId(0L);
        comment.setHasChildComments(false);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setUpdatedAt(System.currentTimeMillis());
        comment.setLikesCount(0L);
        comment.setDislikesCount(0L);

        User user = new User();
        user.setId(42L);
        user.setFirstName("asd");
        user.setLastName("qwe");
        user.setUsername("asddd");

        when(userService.getUserById(42L)).thenReturn(user);

        CommentResponseDTO commentResponseDTO = commentMapper.toDTO(comment);

        assertEquals(12L, commentResponseDTO.getId());
        assertEquals(42L, commentResponseDTO.getUserId());
        assertEquals(1L, commentResponseDTO.getPostId());
        assertEquals("comment.", commentResponseDTO.getContent());
        assertEquals(0L, commentResponseDTO.getParentCommentId());
        assertEquals(false, commentResponseDTO.getHasChildComments());
        assertEquals(0L, commentResponseDTO.getLikesCount());
        assertEquals(0L, commentResponseDTO.getDislikesCount());
        assertEquals("asd", commentResponseDTO.getFirstName());
        assertEquals("qwe", commentResponseDTO.getLastName());
        assertEquals("asddd", commentResponseDTO.getUserName());
    }
}
