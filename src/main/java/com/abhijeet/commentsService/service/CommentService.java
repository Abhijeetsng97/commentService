package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.models.dto.response.SearchResponse;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;

import java.io.IOException;

public interface CommentService {
    CommentResponseDTO addCommentToPost(CommentRequestDTO comment, long postId) throws IOException;

    SearchResponse<CommentResponseDTO> getComments(Long postId, String nextToken) throws IOException;

    SearchResponse<CommentResponseDTO> getReplies(Long commentId, String nextToken) throws IOException;

    CommentResponseDTO addReply(CommentRequestDTO commentRequestDTO, Long commentId) throws IOException;

    void updateReaction(Long commentId, String fieldName, Long i) throws IOException;

    Boolean deleteComment(Long commentId) throws IOException;
}
