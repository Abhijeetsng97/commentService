package com.abhijeet.commentsService.models.dto.response;

import lombok.Data;

@Data
public class CommentResponseDTO {
    private Long id;
    private Long parentCommentId;
    private Long postId;
    private String content;
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean hasChildComments;
    private Long createdAt;
    private Long updatedAt;
    private Long likesCount;
    private Long dislikesCount;
}
