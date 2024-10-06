package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.CommentDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;

import java.io.IOException;

public interface CommentService {
    Comment addCommentToPost(CommentDTO comment, long postId) throws IOException;

    SearchResponse<Comment> getComments(Long postId, String nextToken) throws IOException;

    SearchResponse<Comment> getReplies(String commentId, String nextToken) throws IOException;

    Comment addReply(CommentDTO commentDTO, String rowKey) throws IOException;

    void updateReaction(String id, String fieldName, Long i) throws IOException;

    Boolean deleteComment(String commentId) throws IOException;
}
