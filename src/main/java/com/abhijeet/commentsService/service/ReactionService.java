package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.request.ReactionRequestDTO;
import com.abhijeet.commentsService.models.dto.response.ReactionResponseDTO;
import com.abhijeet.commentsService.models.dto.response.SearchResponse;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;

import java.io.IOException;

public interface ReactionService {
    Boolean createReaction(Long commentId, ReactionRequestDTO reactionRequestDTO) throws IOException;

    Boolean deleteReaction(Long commentId, ReactionRequestDTO reactionRequestDTO) throws IOException;

    SearchResponse<ReactionResponseDTO> getReactionUsers(ReactionEntityType reactionEntityType, Long commentId, ReactionType reactionType, String nextToken) throws IOException;
}
