package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.models.dto.ReactionDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;

public interface ReactionService {
    Boolean createReaction(ReactionDTO reactionDTO);

    Boolean deleteReaction(ReactionDTO reactionDTO);

    SearchResponse<User> getUserList(ReactionEntityType reactionEntityType, Long commentId, ReactionType reactionType);
}
