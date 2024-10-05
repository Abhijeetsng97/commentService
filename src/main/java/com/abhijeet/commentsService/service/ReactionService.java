package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.ReactionDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Reaction;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;

import java.io.IOException;

public interface ReactionService {
    Boolean createReaction(String commentKey, ReactionDTO reactionDTO) throws IOException;

    Boolean deleteReaction(String commentKey, ReactionDTO reactionDTO) throws IOException;

    SearchResponse<Reaction> getUserList(ReactionEntityType reactionEntityType, String commentId, ReactionType reactionType, String nextToken) throws IOException;
}
