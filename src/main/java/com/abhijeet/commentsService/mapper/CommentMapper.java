package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.service.UserService;
import com.abhijeet.commentsService.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final HeaderUtil headerUtil;
    private final SnowflakeIdGeneratorService snowflakeIdGeneratorService;
    private final UserService userService;

    public Comment fromDTO(CommentRequestDTO commentRequestDTO, long postId, long parentCommentId) {
        Comment comment = new Comment();
        comment.setParentCommentId(parentCommentId);
        comment.setId(snowflakeIdGeneratorService.getSnowflakeId());
        comment.setUserId(headerUtil.getUserId());
        comment.setPostId(postId);
        comment.setContent(commentRequestDTO.getContent());
        comment.setHasChildComments(false);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setUpdatedAt(System.currentTimeMillis());
        comment.setLikesCount(0L);
        comment.setDislikesCount(0L);
        return comment;
    }

    public CommentResponseDTO toDTO(Comment comment) {
        User user = userService.getUserById(comment.getUserId());
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setUserId(comment.getUserId());
        commentResponseDTO.setPostId(comment.getPostId());
        commentResponseDTO.setContent(comment.getContent());
        commentResponseDTO.setParentCommentId(comment.getParentCommentId());
        commentResponseDTO.setHasChildComments(comment.getHasChildComments());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt());
        commentResponseDTO.setUpdatedAt(comment.getUpdatedAt());
        commentResponseDTO.setLikesCount(comment.getLikesCount());
        commentResponseDTO.setDislikesCount(comment.getDislikesCount());
        commentResponseDTO.setFirstName(user.getFirstName());
        commentResponseDTO.setLastName(user.getLastName());
        commentResponseDTO.setUserName(user.getUsername());
        return commentResponseDTO;
    }
}
