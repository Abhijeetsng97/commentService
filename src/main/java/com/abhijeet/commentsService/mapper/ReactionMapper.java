package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.ReactionRequestDTO;
import com.abhijeet.commentsService.models.dto.response.ReactionResponseDTO;
import com.abhijeet.commentsService.models.entity.Reaction;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.service.UserService;
import com.abhijeet.commentsService.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactionMapper {
    private final HeaderUtil headerUtil;
    private final UserService userService;

    public Reaction fromDTO(Long commentId, ReactionRequestDTO reactionRequestDTO) {
        Reaction reaction = new Reaction();
        reaction.setReactionType(reactionRequestDTO.getReactionType());
        reaction.setReactionEntityType(reactionRequestDTO.getReactionEntityType());
        reaction.setUserId(headerUtil.getUserId());
        reaction.setEntityId(commentId);
        reaction.setCreatedAt(System.currentTimeMillis());
        reaction.setUpdatedAt(System.currentTimeMillis());
        return reaction;
    }

    public ReactionResponseDTO toDTO(Reaction reaction) {
        User user = userService.getUserById(reaction.getUserId());
        ReactionResponseDTO reactionResponseDTO = new ReactionResponseDTO();
        reactionResponseDTO.setUserId(user.getId());
        reactionResponseDTO.setEntityId(reaction.getEntityId());
        reactionResponseDTO.setFirstName(user.getFirstName());
        reactionResponseDTO.setLastName(user.getLastName());
        reactionResponseDTO.setUserName(user.getUsername());
        return reactionResponseDTO;
    }
}
