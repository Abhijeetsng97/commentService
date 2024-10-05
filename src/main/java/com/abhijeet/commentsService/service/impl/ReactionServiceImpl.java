package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.models.dto.ReactionDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.repository.ReactionRepository;
import com.abhijeet.commentsService.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;

    @Override
    public Boolean createReaction(ReactionDTO reactionDTO) {
        return null;
    }

    @Override
    public Boolean deleteReaction(ReactionDTO reactionDTO) {
        return null;
    }

    @Override
    public SearchResponse<User> getUserList(ReactionEntityType reactionEntityType, Long commentId, ReactionType reactionType) {
        return null;
    }
}
